package com.waes.interview.assignment.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSONParseException;
import com.waes.interview.assignment.AssignmentApplication;
import com.waes.interview.assignment.model.dto.request.ContentSaveRequest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.IOException;
import java.util.Base64;
import java.util.Random;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AssignmentApplication.class)
@AutoConfigureMockMvc
abstract class AbstractResourceIntegrationTest {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Random RANDOM = new Random();

    @Autowired
    private MockMvc mvc;

    ResultActions doPost(String uri, String data) throws Exception {
        return mvc.perform(post(uri).content(data).contentType(APPLICATION_JSON));
    }

    <T> T doPostAndReturn(String uri, String data, Class<T> type) throws Exception {
        return doPostAndReturn(uri, data, status().isOk(), type);
    }

    <T> T doPostAndReturn(String uri, String data, ResultMatcher status, Class<T> type) throws Exception {
        MvcResult result = doPost(uri, data)
                .andExpect(status)
                .andReturn();
        return fromJson(result.getResponse().getContentAsString(), type);
    }

    ResultActions doGet(String uri) throws Exception {
        return mvc.perform(get(uri).contentType(APPLICATION_JSON));
    }

    <T> T doGetAndReturn(String uri, Class<T> type) throws Exception {
        return doGetAndReturn(uri, status().isOk(), type);
    }

    <T> T doGetAndReturn(String uri, ResultMatcher status, Class<T> type) throws Exception {
        MvcResult result = doGet(uri)
                .andExpect(status)
                .andReturn();
        return fromJson(result.getResponse().getContentAsString(), type);
    }

    String createBase64JsonData(byte[] bytes) throws JsonProcessingException {
        String base64 = Base64.getEncoder().encodeToString(bytes);
        ContentSaveRequest contentSaveRequest=new ContentSaveRequest();
        contentSaveRequest.setEncodedData(base64);
        return mapper.writeValueAsString(contentSaveRequest);
    }


    String createBase64JsonData() throws JsonProcessingException {
        byte[] buffer = new byte[1024];
        RANDOM.nextBytes(buffer);
        return createBase64JsonData(buffer);
    }


    private <T> T fromJson(String data, Class<T> type) throws IOException {
        return mapper.readValue(data, type);
    }


    public <T> T deserializeJsonToString(String data,Class<T> type){
        T result =null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(data, type);
        }catch (JsonMappingException e){

        }catch (JSONParseException e){

        }catch (IOException e){

        }
        return result;
    }
}
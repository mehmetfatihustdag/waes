package com.waes.interview.assignment.resource;

import com.waes.interview.assignment.model.dto.request.ContentSaveRequest;
import com.waes.interview.assignment.model.dto.response.ContentDiffDetailInfo;
import com.waes.interview.assignment.model.dto.response.ContentDiffResponse;
import com.waes.interview.assignment.model.dto.response.ContentDiffResultType;
import com.waes.interview.assignment.model.dto.response.ContentSaveResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.Random;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContentResourceTest extends AbstractResourceIntegrationTest {

    private static String ENDPOINT_LEFT ="/v1/diff/%s/left" ;
    private static String ENDPOINT_RIGHT = "/v1/diff/%s/right";
    private static String ENDPOINT_DIFF = "/v1/diff/%s";



    private Long id;
    ResultMatcher created = MockMvcResultMatchers.status().isCreated();

    @Before
    public void setUp() {
        this.id = new Random().nextLong();
    }

    @Test
    public void doDiffEquals() throws Exception {
        String data = createBase64JsonData();
        ContentSaveResponse response = doPostAndReturn(String.format(ENDPOINT_LEFT,id), data,created, ContentSaveResponse.class);
        ContentSaveResponse expected = new ContentSaveResponse(id,Optional.ofNullable(deserializeJsonToString(data, ContentSaveRequest.class)).get().getEncodedData());
        assertEquals("response and expected asserted", response, expected);

        response = doPostAndReturn(String.format(ENDPOINT_RIGHT,id), data,created, ContentSaveResponse.class);
        assertEquals("response and expected asserted", response.getTransactionId(), expected.getTransactionId());

        ContentDiffResponse difference = doGetAndReturn(String.format(ENDPOINT_DIFF,id), ContentDiffResponse.class);
        ContentDiffResponse expectedDiffResult = new ContentDiffResponse();
        expectedDiffResult.setMessage(ContentDiffResultType.EQUAL.toString());
        assertEquals("Response matches expectation", difference,expectedDiffResult );
    }

    @Test
    public void doDiffNoEqualsDueToSize() throws Exception {
        String dataLeft = createBase64JsonData(new byte[11]);
        ContentSaveResponse response = doPostAndReturn(String.format(ENDPOINT_LEFT,id), dataLeft,created, ContentSaveResponse.class);
        ContentSaveResponse expected = new ContentSaveResponse(id,Optional.ofNullable(deserializeJsonToString(dataLeft,ContentSaveRequest.class)).get().getEncodedData());
        assertEquals("response and expected asserted", response, expected);

        String dataRight = createBase64JsonData(new byte[12]);
        response = doPostAndReturn(String.format(ENDPOINT_RIGHT,id), dataRight,created , ContentSaveResponse.class);
        ContentSaveResponse expectedSecond = new ContentSaveResponse(id,Optional.ofNullable(deserializeJsonToString(dataRight,ContentSaveRequest.class)).get().getEncodedData());
        assertEquals("Response matches expectation", response, expectedSecond);

        ContentDiffResponse differences = doGetAndReturn(String.format(ENDPOINT_DIFF,id), ContentDiffResponse.class);
        ContentDiffResponse expectedDiffResult = new ContentDiffResponse();
        expectedDiffResult.setMessage(ContentDiffResultType.DIFFERENT_LENGTH.toString());
        assertEquals("Message is as expected", differences,expectedDiffResult);

    }


   @Test
    public void doDiffNoEquals() throws Exception {
        final byte[] right_byte_array = "ewogIm5hbWUiOiJmYXRpaCIKfQ==".getBytes();
        final byte[] left_right_array = "ewogIm5hbWUiOiJzYXRpaCIKfQ==".getBytes();
        final String leftData = createBase64JsonData(left_right_array);
        final String rightData = createBase64JsonData(right_byte_array);

        doPostAndReturn(String.format(ENDPOINT_LEFT,id), leftData,created, ContentSaveResponse.class);
        doPostAndReturn(String.format(ENDPOINT_RIGHT,id), rightData,created, ContentSaveResponse.class);

        ContentDiffResponse expectation = new ContentDiffResponse("Byte arrays are NOT equal!", asList(
                new ContentDiffDetailInfo(15, 1)
        ));
        expectation.setMessage(ContentDiffResultType.NOT_EQUAL.toString());

        ContentDiffResponse differences = doGetAndReturn(String.format(ENDPOINT_DIFF,id), ContentDiffResponse.class);

        assertEquals("Diff Expectation ", differences,expectation);

    }


}



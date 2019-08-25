package com.waes.interview.assignment.service;

import com.waes.interview.assignment.exception.BusinessBadRequestException;
import com.waes.interview.assignment.model.dto.request.ContentSaveRequest;
import com.waes.interview.assignment.model.dto.response.ContentDiffResponse;
import com.waes.interview.assignment.model.dto.validation.ContentDiffDataValidationDTO;
import com.waes.interview.assignment.model.dto.validation.ContentSaveValidationDTO;
import com.waes.interview.assignment.model.entity.Content;
import com.waes.interview.assignment.model.entity.ContentType;
import com.waes.interview.assignment.repository.ContentRepository;
import com.waes.interview.assignment.service.component.IContentCompareDiffer;

import com.waes.interview.assignment.validation.ContentValidation;
import com.waes.interview.assignment.validation.IContentValidation;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ContentServiceTest {

    @Mock
    IContentValidation<ContentSaveValidationDTO, ContentDiffDataValidationDTO,Long> contentValidation;

    @Mock
    private ContentRepository contentRepository;

    @Mock
    private IContentCompareDiffer<byte[], ContentDiffResponse> contentDiffOperation;

    @InjectMocks
    private ContentService contentService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void failWithNullRequest() {
        contentService.save(null,12L, ContentType.RIGHT);
    }



    @Test
    public void when_save_left_content_it_should_return_content() {
        Content dummyContent = new Content();
        dummyContent.setContentType(ContentType.LEFT);
        dummyContent.setTransactionId(11l);
        dummyContent.setEncodedData("sss");
        when(contentRepository.save(any(Content.class))).thenReturn(dummyContent);

        ContentSaveRequest contentSaveRequest = new ContentSaveRequest();
        contentSaveRequest.setEncodedData("sss");
        Content result =contentService.save(contentSaveRequest,11l,ContentType.LEFT);

        assertThat("result", result, Matchers.is(sameInstance(dummyContent)));
        verify(contentRepository).save(result);

    }

    @Test
    public void when_save_right_content_it_should_return_content() {
        Content dummyContent = new Content();
        dummyContent.setContentType(ContentType.RIGHT);
        dummyContent.setTransactionId(11l);
        dummyContent.setEncodedData("sss");
        when(contentRepository.save(any(Content.class))).thenReturn(dummyContent);

        ContentSaveRequest contentSaveRequest = new ContentSaveRequest();
        contentSaveRequest.setEncodedData("sss");
        Content result =contentService.save(contentSaveRequest,11l,ContentType.RIGHT);

        assertThat("result", result, Matchers.is(sameInstance(dummyContent)));
        verify(contentRepository).save(result);
    }


    @Test
    public void when_tsave_right__content() {
        Content dummyContent = new Content();
        dummyContent.setContentType(ContentType.RIGHT);
        dummyContent.setTransactionId(11l);
        dummyContent.setEncodedData("sss");
        when(contentRepository.save(any(Content.class))).thenReturn(dummyContent);

        ContentSaveRequest contentSaveRequest = new ContentSaveRequest();
        contentSaveRequest.setEncodedData("sss");
        Content result =contentService.save(contentSaveRequest,11l,ContentType.RIGHT);

        assertThat("result", result, Matchers.is(sameInstance(dummyContent)));
        verify(contentRepository).save(result);
    }




}

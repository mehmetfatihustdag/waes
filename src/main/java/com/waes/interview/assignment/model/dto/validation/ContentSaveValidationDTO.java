package com.waes.interview.assignment.model.dto.validation;

import com.waes.interview.assignment.model.dto.request.ContentSaveRequest;
import com.waes.interview.assignment.model.entity.ContentType;

public class ContentSaveValidationDTO {

    private ContentSaveRequest contentSaveRequest;
    private Long transactionId;
    private ContentType contentType;

    public ContentSaveValidationDTO(ContentSaveRequest contentSaveRequest, Long transacitonId, ContentType contentType) {
        this.contentSaveRequest = contentSaveRequest;
        this.transactionId = transacitonId;
        this.contentType = contentType;
    }

    public ContentSaveRequest getContentSaveRequest() {
        return contentSaveRequest;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public ContentType getContentType() {
        return contentType;
    }

}

package com.waes.interview.assignment.model.dto.response;

import com.waes.interview.assignment.model.dto.request.ContentSaveRequest;
import com.waes.interview.assignment.resource.ContentResource;

import java.util.Objects;

/**
 * Class represents as response dto for save endpoints
 *   * {@link ContentResource#saveLeftDiffData(Long, ContentSaveRequest),
 *  *  {@link ContentResource#saveRightDiffData(Long, ContentSaveRequest)}
 *     @author Fatih Ustdag
 **/
public class ContentSaveResponse {
    private Long transactionId;
    private String encodedData;

    public ContentSaveResponse() {

    }

    public ContentSaveResponse(Long transactionId, String encodedData) {
        this.transactionId = transactionId;
        this.encodedData = encodedData;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public String getEncodedData() {
        return encodedData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentSaveResponse)) return false;
        ContentSaveResponse that = (ContentSaveResponse) o;
        return Objects.equals(getTransactionId(), that.getTransactionId()) &&
                Objects.equals(getEncodedData(), that.getEncodedData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransactionId(), getEncodedData());
    }
}

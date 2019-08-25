package com.waes.interview.assignment.model.dto.request;


import com.waes.interview.assignment.resource.ContentResource;

import java.util.Objects;

/**
 * Request DTO Class represents that payload body for save endpoints
 * {@link ContentResource#saveLeftDiffData(Long, ContentSaveRequest),
 *  {@link ContentResource#saveRightDiffData(Long, ContentSaveRequest)}
 * @author Fatih Ustdag
 *
 */
public class ContentSaveRequest {
    private String encodedData;

    public ContentSaveRequest(){

    }

    public String getEncodedData() {
        return encodedData;
    }

    public void setEncodedData(String encodedData) {
        this.encodedData = encodedData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentSaveRequest)) return false;
        ContentSaveRequest that = (ContentSaveRequest) o;
        return getEncodedData().equals(that.getEncodedData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEncodedData());
    }
}

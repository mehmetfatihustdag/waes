package com.waes.interview.assignment.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waes.interview.assignment.resource.ContentResource;

import java.util.List;
import java.util.Objects;

/**
 * Class represents as response dto for content diff endpoint
 * {@link ContentResource#getContentDifferenceResult(Long)}
 * @author Fatih Ustdag
 */
public class ContentDiffResponse {

    private String message;

    //If there is no difference do not add json
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private  List<ContentDiffDetailInfo> differences;

    public ContentDiffResponse() {

    }

    public ContentDiffResponse(String message, List<ContentDiffDetailInfo> differences) {
        this.message = message;
        this.differences = differences;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ContentDiffDetailInfo> getDifferences() {
        return differences;
    }

    public void setDifferences(List<ContentDiffDetailInfo> differences) {
        this.differences = differences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentDiffResponse)) return false;
        ContentDiffResponse that = (ContentDiffResponse) o;
        return Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getDifferences(), that.getDifferences());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getDifferences());
    }
}



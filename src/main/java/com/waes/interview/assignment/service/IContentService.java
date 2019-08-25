package com.waes.interview.assignment.service;

import com.waes.interview.assignment.model.dto.request.ContentSaveRequest;
import com.waes.interview.assignment.model.dto.response.ContentDiffResponse;
import com.waes.interview.assignment.model.entity.Content;
import com.waes.interview.assignment.model.entity.ContentType;
/**
 *  Interface that holds the method for service operations
 * @author Fatih Ustdag
 */
public interface IContentService {

    /**  Method for save operation for content data
     * @param contentSaveRequest
     * @param transactionId
     * @return ContentSaveResponse
     **/
    Content save(ContentSaveRequest contentSaveRequest, Long transactionId, ContentType contentType);

    /**  Method for diff operation
     * @param transactionId
     * @return ContentDiffResponse
     **/
    ContentDiffResponse diff(Long transactionId);
}

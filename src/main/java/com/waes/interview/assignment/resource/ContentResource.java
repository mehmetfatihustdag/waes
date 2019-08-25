package com.waes.interview.assignment.resource;

import com.waes.interview.assignment.model.dto.request.ContentSaveRequest;
import com.waes.interview.assignment.model.dto.response.ContentDiffResponse;
import com.waes.interview.assignment.model.entity.Content;
import com.waes.interview.assignment.service.IContentService;
import com.waes.interview.assignment.model.dto.response.ContentSaveResponse;
import com.waes.interview.assignment.model.entity.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.net.URI;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Web Resource for providing endpoints for
 *    Storage for left and right side of data {@link #saveLeftDiffData} {@link #saveRightDiffData}
 *    Retrieving of difference of left and right side of data {@link #getContentDifferenceResult}
 *    @author Fatih Ustdag
 */
@RestController
@RequestMapping("v1/diff/")
public class ContentResource {

    private static final Logger log = LogManager.getLogger(ContentResource.class);

    private final IContentService contentService;

    /**
     * Constructor used to inject contentService interface
     *@param contentService The interface which aims to store left,right encoded data, and compare them
     *                      Default implementation is ContentService
     */
    @Autowired
    public ContentResource(IContentService contentService) {
        this.contentService = contentService;
    }


    /**
     * Rest Api Endpoint for saving left side of data
     *
     * @param transactionId      ID for the operation
     * @param contentSaveRequest Request which holds
     * @return {@link ContentSaveResponse ContentSaveResponse} holds status of the result and content which send to server
     */
    @PostMapping(value = "{id}/left", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ContentSaveResponse> saveLeftDiffData(@PathVariable("id") @Min(1) @Max(Long.MAX_VALUE) @NotNull(message = "Transaction Id can not be null") final Long transactionId,
                                                                @Valid  @RequestBody final ContentSaveRequest contentSaveRequest) {
        log.info("Retrieved left content: {} for transaction id: {}", contentSaveRequest.getEncodedData(), transactionId);
        return getContentSaveResponseEntity(contentSaveRequest,transactionId,ContentType.LEFT);
    }

    /**
     * Rest Api Endpoint for saving right side of data
     *
     * @param transactionId      ID for the operation
     * @param contentSaveRequest Request which holds
     * @return {@link ContentSaveResponse ContentSaveResponse} holds status of the result and content which send to server
     */
    @PostMapping(value = "{id}/right", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ContentSaveResponse> saveRightDiffData(@PathVariable("id") final Long transactionId,
                                                                @RequestBody  final ContentSaveRequest contentSaveRequest) {
        log.info("Received left value: {} for transaction id: {}", contentSaveRequest.getEncodedData(),transactionId);
        return getContentSaveResponseEntity(contentSaveRequest,transactionId,ContentType.RIGHT);
    }

    /**
     * Helper method which holds common operations of saveRightDiffData and saveLeftDiffData
     *@param contentSaveRequest Request which holds
     *@param transactionId      ID for the operation
     *@param contentType    Enum holds content type RIGHT,LEFT
     *
     * @return {@link ContentSaveResponse ContentSaveResponse} holds status of the result and content which send to server
     */
    private ResponseEntity<ContentSaveResponse> getContentSaveResponseEntity(ContentSaveRequest contentSaveRequest, Long transactionId, ContentType contentType) {
        Content content = contentService.save(contentSaveRequest,transactionId,contentType);
        ContentSaveResponse contentSaveResponse = new ContentSaveResponse(content.getTransactionId(),content.getEncodedData());
        final URI uri =
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .buildAndExpand(contentSaveResponse.getTransactionId())
                        .toUri();
        return ResponseEntity.created(uri).body(contentSaveResponse);
    }

    /**
     * Rest Api Endpoint retrieving the difference of left and right side of data
     *
     * @param transactionId   Id for the operation
     * @return {@link ContentDiffResponse ContentDiffResponse}
     * holds status off diff and lists of diff result (offset, and positions)
     */
    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ContentDiffResponse> getContentDifferenceResult(@PathVariable("id")  final Long transactionId) {
        log.info("Diff Process have already started for transaction id: {}", transactionId);
        ContentDiffResponse contentGetDifferenceResponse =contentService.diff(transactionId);
        return ResponseEntity.ok(contentGetDifferenceResponse);
    }
}








package com.waes.interview.assignment.service;

import com.waes.interview.assignment.exception.InConsistentDataException;
import com.waes.interview.assignment.model.dto.request.ContentSaveRequest;
import com.waes.interview.assignment.model.dto.response.ContentDiffResponse;
import com.waes.interview.assignment.model.dto.validation.ContentDiffDataValidationDTO;
import com.waes.interview.assignment.model.dto.validation.ContentSaveValidationDTO;
import com.waes.interview.assignment.model.entity.Content;
import com.waes.interview.assignment.model.entity.ContentType;
import com.waes.interview.assignment.repository.ContentRepository;
import com.waes.interview.assignment.service.component.IContentCompareDiffer;
import com.waes.interview.assignment.util.ApplicationConstants;
import com.waes.interview.assignment.validation.ContentValidation;
import com.waes.interview.assignment.validation.IContentValidation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;

/**
 *  Mongodb Implementation of {@link IContentService}
 * @author Fatih Ustdag
 */
@Service
public class ContentService implements IContentService{

  private static final Logger log = LogManager.getLogger(ContentValidation.class);
  private final ContentRepository contentRepository;
  private final IContentValidation<ContentSaveValidationDTO,ContentDiffDataValidationDTO,Long> contentValidation;
  private final IContentCompareDiffer<byte[], ContentDiffResponse> contentDiffOperation;

  @Autowired
  public ContentService(ContentRepository contentRepository,
                        IContentValidation<ContentSaveValidationDTO,ContentDiffDataValidationDTO,Long> contentValidation,
                        IContentCompareDiffer<byte[],ContentDiffResponse> contentDiffOperation){
        this.contentRepository = contentRepository;
        this.contentValidation = contentValidation;
        this.contentDiffOperation = contentDiffOperation;
  }


  @Override
  public Content save(final ContentSaveRequest contentSaveRequest, final Long transactionId, final ContentType contentType){
    contentValidation.validateSave(new ContentSaveValidationDTO(contentSaveRequest,transactionId,contentType));
    return contentRepository.save(mapToContent(contentSaveRequest,transactionId,contentType));
  }

  private Content mapToContent(ContentSaveRequest contentSaveRequest, Long transactionId, ContentType contentType){
      Content content = new Content();
      content.setId(UUID.randomUUID());
      content.setContentType(contentType);
      content.setEncodedData(contentSaveRequest.getEncodedData());
      content.setTransactionId(transactionId);
      return content;
  }


  @Override
  public ContentDiffResponse diff(final Long transactionId) {
      contentValidation.validateTransactionId(transactionId);
    final List<Content> contents = getContentList(transactionId);
    final Map<ContentType, Content> map = contents.stream()
            .collect(Collectors.toMap(Content::getContentType, content -> content));
    final Optional<String> leftData = Optional.
            ofNullable(map.get(ContentType.LEFT).getEncodedData());
    final Optional<String> rightData = Optional.
            ofNullable(map.get(ContentType.RIGHT).getEncodedData());
    contentValidation.validateDiff(new ContentDiffDataValidationDTO(leftData.get(),
                                       rightData.get()));
    final ContentDiffResponse contentDiffResponse = contentDiffOperation.doDiff(decodeBase64(rightData.get()),
            decodeBase64(leftData.get()));
    return contentDiffResponse;
  }

  private List<Content> getContentList(final Long transactionId) {
   final List<Content> contents = contentRepository.findContentsByTransactionId(transactionId);
   validateContent(contents,transactionId);
   return contents;
  }

  private void validateContent(List<Content> contents,Long transactionId){
      if (CollectionUtils.isEmpty(contents)) {
          log.error("No data exists for compare for {transactionId} : " + transactionId);
          throw new InConsistentDataException(ApplicationConstants.NO_DATA_EXISTS);
      }
      if (CollectionUtils.isEmpty(contents) || contents.size() != 2) {
          log.error("Inconsistent exists for compare for {transactionId} : " + transactionId);
          throw new InConsistentDataException(ApplicationConstants.INCONSISTENT_DATA_EXISTS);
      }

  }


}

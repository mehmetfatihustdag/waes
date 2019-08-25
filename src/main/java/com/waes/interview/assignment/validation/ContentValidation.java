package com.waes.interview.assignment.validation;

import com.waes.interview.assignment.exception.BusinessBadRequestException;
import com.waes.interview.assignment.exception.ContentAlreadyExistsException;
import com.waes.interview.assignment.exception.ContentNotFoundException;
import com.waes.interview.assignment.model.dto.validation.ContentDiffDataValidationDTO;
import com.waes.interview.assignment.model.dto.validation.ContentSaveValidationDTO;
import com.waes.interview.assignment.model.entity.ContentType;
import com.waes.interview.assignment.repository.ContentRepository;
import com.waes.interview.assignment.util.ApplicationConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Objects;
import java.util.Optional;


/**
 *  Implementation of IContent Validation
 * @author Fatih Ustdag
 */
@Component
public class ContentValidation implements IContentValidation<ContentSaveValidationDTO, ContentDiffDataValidationDTO,Long>{

    private static final Logger log = LogManager.getLogger(ContentValidation.class);
    private ContentRepository contentRepository;

    public ContentValidation(ContentRepository contentRepository){
        this.contentRepository = contentRepository;
    }

    @Override
    public void validateSave(ContentSaveValidationDTO dto) {
       validateSaveOperation(dto);
    }

    private void validateSaveOperation(ContentSaveValidationDTO dto){
        validateTransactionId(dto.getTransactionId());
        validatePayloadData(dto.getContentSaveRequest().getEncodedData(),
                dto.getTransactionId(),dto.getContentType());
    }

    @Override
    public void validateDiff(ContentDiffDataValidationDTO dto) {
        validateDataToDiff(dto.getLeftData(),dto.getRightData());
    }

    private void validateDataToDiff(String leftData, String rightData) {
        if (StringUtils.isEmpty(leftData)) {
            throw new ContentNotFoundException(ApplicationConstants.LEFT_DATA_CAN_NOT_FOUND);
        }
        if (StringUtils.isEmpty(rightData)) {
            throw new ContentNotFoundException(ApplicationConstants.RIGHT_DATA_CAN_NOT_FOUND);
        }
    }

    @Override
    public void validateTransactionId(Long transactionId){
        if(!Optional.ofNullable(transactionId).isPresent()){
            log.error("No transaction Id send for the content content");
            throw  new BusinessBadRequestException(ApplicationConstants.INVALID_TRANSACTION_ID);
        }
    }

    private void validatePayloadData(String encodedData, Long transactionId,ContentType contentType){
        if(!Optional.ofNullable(encodedData).isPresent()){
            log.error("No payload content for {transaction Id : }" +transactionId );
            throw  new BusinessBadRequestException(ApplicationConstants.NO_PAYLOAD_CONTENT);
        }
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            decoder.decode(encodedData);
        } catch(IllegalArgumentException iae) {
            log.error("No payload content is not valid encoded base64 string for  {transaction Id : }" + encodedData );
            throw  new BusinessBadRequestException(ApplicationConstants.ENCODED_STRING_NOT_VALID);
        }

        if(Objects.nonNull(contentRepository.findContentByTransactionIdAndContentType(transactionId,contentType))){
            log.error(ApplicationConstants.CONTENT_ALREADY_EXISTS + "for {transaction Id : }" + transactionId);
            throw new ContentAlreadyExistsException(ApplicationConstants.CONTENT_ALREADY_EXISTS);
        }

    }
}

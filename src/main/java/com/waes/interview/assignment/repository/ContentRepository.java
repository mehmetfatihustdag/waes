package com.waes.interview.assignment.repository;

import com.waes.interview.assignment.model.entity.Content;
import com.waes.interview.assignment.model.entity.ContentType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface to do database operation on MongoDB  via Spring MongoDB JPA.
 * <p>
 * @author Fatih Ustdag */
@Repository
public interface ContentRepository extends MongoRepository<Content, UUID> {
    /**
     * database method which return Left and Right side of data filter by transactionId
     *
     * @param  transactionId Id for operation
     * @return {@link  List<Content>} Left and Right side of data filter by transactionId
     */
    List<Content> findContentsByTransactionId(Long transactionId);

    /**
     * database method which return data filtered by transactionId and Con
     *
     * @param  transactionId Id for operation
     * @param  contentType indicates type of the content (left or right)
     * @return {@link  Content} Left and Right side of data filter by transactionId
     */
    Content findContentByTransactionIdAndContentType(Long transactionId, ContentType contentType);

}

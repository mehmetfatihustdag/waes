package com.waes.interview.assignment.model.entity;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Entity that represents the "content" collections
 * @author Fatih Ustdag
 */
@Document("content")
public class Content extends BaseEntity {

    private String encodedData;
    private ContentType contentType;

    @NotNull
    @Indexed(name = "transaction_id_index")
    private Long transactionId;

    public Content (){

    }


    public String getEncodedData() {
        return encodedData;
    }

    public void setEncodedData(String encodedData) {
        this.encodedData = encodedData;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Content)) return false;
        Content content = (Content) o;
        return getEncodedData().equals(content.getEncodedData()) &&
                getContentType() == content.getContentType() &&
                getTransactionId().equals(content.getTransactionId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEncodedData(), getContentType(), getTransactionId());
    }
}


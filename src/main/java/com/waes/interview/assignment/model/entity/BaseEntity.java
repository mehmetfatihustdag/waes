package com.waes.interview.assignment.model.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.UUID;


/**
 * Entity that represents base model for mongodb collections
 * @author Fatih Ustdag
 */

@Document
public abstract class BaseEntity {
    @Id
    private UUID id;

    @Version
    private Long version;

    @Field( "created_date")
    @CreatedDate
    private Instant createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

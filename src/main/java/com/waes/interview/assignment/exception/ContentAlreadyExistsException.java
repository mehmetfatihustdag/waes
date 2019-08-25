package com.waes.interview.assignment.exception;

/**
 * Exception which indicates that  Content Already Exists in database
 * return status 400
 * @author Fatih Ustdag
 *
 */
public class ContentAlreadyExistsException extends BusinessBadRequestException {
    public ContentAlreadyExistsException(String message) {
        super(message);
    }
}

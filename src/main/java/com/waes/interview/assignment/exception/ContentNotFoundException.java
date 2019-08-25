package com.waes.interview.assignment.exception;
/**
 * Exception which indicates that Content Not found in database
 * return status 404
 * @author Fatih Ustdag
 *
 */
public class ContentNotFoundException  extends BusinessNotFoundException {
    public ContentNotFoundException(final String message) {
        super(message);
    }
}

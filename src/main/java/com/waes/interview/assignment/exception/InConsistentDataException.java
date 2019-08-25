package com.waes.interview.assignment.exception;

/**
 * Exception which indicates that Inconsistent data exists
 * (- left data exists but right one not exists,
 *  - right data exists but left one not exists)
 * return status 404
 * @author Fatih Ustdag
 *
 */
public class InConsistentDataException extends BusinessBadRequestException {
    public InConsistentDataException(final String message) {
        super(message);
    }
}

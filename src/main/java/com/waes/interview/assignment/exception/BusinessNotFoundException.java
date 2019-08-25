package com.waes.interview.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Exception indicates for Not Found  Exception
 *  HTTPStatus 404
 * @author Fatih Ustdag
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BusinessNotFoundException extends RuntimeException{
    public BusinessNotFoundException(final String message) {
        super(message);
    }
}

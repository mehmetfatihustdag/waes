package com.waes.interview.assignment.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Exception indicates for Bad Request Exception
 *  HTTPStatus 400
 * @author Fatih Ustdag
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessBadRequestException extends RuntimeException{

    public BusinessBadRequestException(final String message) {
        super(message);
    }
}

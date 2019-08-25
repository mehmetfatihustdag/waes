package com.waes.interview.assignment.exception.handler;
import com.waes.interview.assignment.exception.BusinessBadRequestException;
import com.waes.interview.assignment.exception.BusinessNotFoundException;
import com.waes.interview.assignment.model.dto.error.ErrorDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Handle any Exception that extends from BusinessNotFoundException returning a HTTP status code NOT_FOUND (404)
     *
     * @param e Exception to be handled
     * @return {@link ErrorDTO} containing the error message
     */
    @ExceptionHandler(BusinessNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFoundException(BusinessNotFoundException e) {
        final ErrorDTO error = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handle any Exception that extends from BusinessBadRequestException returning a HTTP status code BAD_REQUEST (400)
     *
     * @param e Exception to be handled
     * @return {@link ErrorDTO} containing the error message
     */
    @ExceptionHandler(BusinessBadRequestException.class)
    public ResponseEntity<ErrorDTO> handleBadRequestException(BusinessBadRequestException e) {
        final ErrorDTO error = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handle any unexpected Exception returning a HTTP status code INTERNAL_SERVER_ERROR (500)
     *
     * @param e Exception to be handled
     * @return {@link ErrorDTO} containing the error message
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorDTO> handleThrowable(Throwable e) {
        final ErrorDTO error = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}

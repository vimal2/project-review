package com.revhire.employerservice.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a bad request is made
 */
public class BadRequestException extends ApiException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, message, cause);
    }
}

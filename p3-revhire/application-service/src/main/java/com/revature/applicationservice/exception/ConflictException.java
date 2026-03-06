package com.revature.applicationservice.exception;

public class ConflictException extends ApiException {

    public ConflictException(String message) {
        super(message, 409);
    }
}

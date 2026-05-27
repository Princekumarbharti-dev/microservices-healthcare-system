package com.authservice.auth_service.exception;

public class MissingFieldException extends RuntimeException {
    public MissingFieldException(String message) {
        super(message);
    }
}
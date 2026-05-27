package com.authservice.auth_service.exception;

public class OldPasswordIncorrectException extends RuntimeException {
    public OldPasswordIncorrectException(String message) {
        super(message);
    }
}
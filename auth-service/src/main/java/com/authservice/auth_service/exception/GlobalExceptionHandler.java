package com.authservice.auth_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentValidation(MethodArgumentNotValidException exception) {
        Map<String, String> error = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            error.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        error.put("TimeStamp", LocalDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEmailNotFound(EmailNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("TimeStamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPassword(InvalidPasswordException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("TimeStamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(OldPasswordIncorrectException.class)
    public ResponseEntity<Map<String, String>> handleOldPasswordIncorrect(OldPasswordIncorrectException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("TimeStamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<Map<String, String>> handlePasswordMismatch(PasswordMismatchException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("TimeStamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MissingFieldException.class)
    public ResponseEntity<Map<String, String>> handleMissingField(MissingFieldException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("TimeStamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, String>> handleInvalidToken(InvalidTokenException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("TimeStamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("TimeStamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
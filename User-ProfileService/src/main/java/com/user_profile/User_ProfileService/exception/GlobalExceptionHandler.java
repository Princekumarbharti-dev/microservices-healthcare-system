package com.user_profile.User_ProfileService.exception;

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

        Map<String, String> errs = new HashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(er -> errs.put(er.getField(), er.getDefaultMessage()));

        errs.put("TimeStamp", LocalDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errs);
    }

    @ExceptionHandler(EmailAlreadyExists.class)
    public ResponseEntity<Map<String,String>> emailAlreadyExists(EmailAlreadyExists exception){

        Map<String,String> error = new HashMap<>();
        error.put("message", exception.getMessage());
        error.put("TimeStamp", LocalDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(EmailNotFound.class)
    public ResponseEntity<Map<String,String>> emailNotFound(EmailNotFound exception){

        Map<String,String> error = new HashMap<>();
        error.put("message", exception.getMessage());
        error.put("TimeStamp", LocalDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UsersIDNotFound.class)
    public ResponseEntity<Map<String,String>> userIdNotFound(UsersIDNotFound exception){

        Map<String,String> error = new HashMap<>();
        error.put("message", exception.getMessage());
        error.put("TimeStamp", LocalDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(PasswordNotMatch.class)
    public ResponseEntity<Map<String,String>> passwordNotMatched(PasswordNotMatch exception){

        Map<String,String> error = new HashMap<>();
        error.put("message", exception.getMessage());
        error.put("TimeStamp", LocalDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
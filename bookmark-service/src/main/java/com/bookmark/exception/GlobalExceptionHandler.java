package com.bookmark.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Map<String,String> runtimeException(RuntimeException exception){
        Map<String,String> error=new HashMap<>();
        error.put("error",exception.getMessage());
        error.put("timestamp",LocalDateTime.now().toString());
        return error;
    }

}

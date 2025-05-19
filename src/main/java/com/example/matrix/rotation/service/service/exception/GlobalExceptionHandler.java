package com.example.matrix.rotation.service.service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRotationException.class)
    public ResponseEntity<Map<String,String>> handleInvalidRotation(InvalidRotationException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of("error", ex.getMessage()));
    }
}

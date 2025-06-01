package com.ecommerce.spring_ecommerce.error_controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseException(ResponseStatusException e) {
        return new ResponseEntity<String>(e.getReason(), e.getStatusCode());
    }
}

package com.ecommerce.spring_ecommerce.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public record ApiErrorResponse<T>(String message, Map<String, String> errors, T data, Object metadata, String path) {

    public ApiErrorResponse(String message, Map<String, String> errors, T data, String path) {
        this(message, errors, data, null, path);
    }

    public ApiErrorResponse(String message, Map<String, String> errors, String path) {
        this(message, errors, null, null, path);
    }

    public ApiErrorResponse(String message, String path) {
        this(message, null, null, path);
    }

    public ApiErrorResponse(T data, String path) {
        this(HttpStatus.OK.name(), null, data, null, path);
    }

}

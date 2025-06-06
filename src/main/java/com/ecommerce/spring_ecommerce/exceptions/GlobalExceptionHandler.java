package com.ecommerce.spring_ecommerce.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse<?>> handleResponseStatusException(ResponseStatusException e,
                                                                             HttpServletRequest request) {
        return new ResponseEntity<ApiErrorResponse<?>>(new ApiErrorResponse<>(e.getStatusCode().toString(), new HashMap<String,
                String>(Map.of("1", e.getReason() != null ? e.getReason() : "")),
                request.getRequestURI()),
                e.getStatusCode());
    }

    //Handle hiberate validator exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();

        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        for (int i = 0; i < errors.size(); i++) {
            ObjectError error = errors.get(i);
            String fieldName = ((FieldError) error).getField() + " (" + (i + 1) + ")";
            String errorMessage = error.getDefaultMessage();

            response.put(fieldName, errorMessage);
        }

        return new ApiErrorResponse<>("One or more fields are invalid.", response, request.getRequestURI());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e,
                                                                  HttpServletRequest request) {
        String message = e.getMessage();
        return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<String> handleAPIException(APIException e, HttpServletRequest request) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}

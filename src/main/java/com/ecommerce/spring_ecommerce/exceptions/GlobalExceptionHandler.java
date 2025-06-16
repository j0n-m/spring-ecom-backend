package com.ecommerce.spring_ecommerce.exceptions;

import com.ecommerce.spring_ecommerce.payload.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse<?> handleMethodArgumentNotValidException(ConstraintViolationException e, HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();
        List<ConstraintViolation<?>> errors = e.getConstraintViolations().stream().toList();

        for (int i = 0; i < errors.size(); i++) {
            ConstraintViolation<?> error = errors.get(i);
            System.out.println(error);
            String fieldName = error.getPropertyPath() + " (" + (i + 1) + ")";
            String errorMessage = error.getMessageTemplate();

            response.put(fieldName, errorMessage);
        }

        return new ApiErrorResponse<>("One or more fields are invalid.", response, request.getRequestURI());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> handleResourceNotFoundException(ResourceNotFoundException e,
                                                                       HttpServletRequest request) {
        String message = e.getMessage();
        APIResponse apiResponse = new APIResponse(message, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> handleAPIException(APIException e, HttpServletRequest request) {
        String message = e.getMessage();
        APIResponse apiResponse = new APIResponse(message, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}

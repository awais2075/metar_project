package com.metar.exception;

import com.metar.model.ApiError;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class RestControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleException(Exception ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errors, ex);
    }

    @ExceptionHandler(SubscriptionFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleSubscriptionFoundException(SubscriptionFoundException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleSubscriptionNotFoundException(SubscriptionNotFoundException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MetarNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMetarNotFoundException(MetarNotFoundException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ApiError buildErrorResponse(HttpStatus status, String message) {
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.getReasonPhrase())
                .message(message)
                .build();
    }

    private ApiError buildErrorResponse(HttpStatus status, Throwable ex) {
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.getReasonPhrase())
                .message(ex.getMessage())
                .trace(ExceptionUtils.getStackTrace(ex))
                .build();
    }

    private ApiError buildErrorResponse(HttpStatus status, Map<String, String> errors,Throwable ex) {
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.getReasonPhrase())
                .message(ex.getMessage())
                .errors(errors)
                .build();
    }
}

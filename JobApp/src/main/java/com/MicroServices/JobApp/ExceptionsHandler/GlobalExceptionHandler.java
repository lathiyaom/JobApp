package com.MicroServices.JobApp.ExceptionsHandler;

import com.MicroServices.JobApp.Constant.HttpStatusCodeEnum;
import com.MicroServices.JobApp.Exceptions.ResourceNotFoundException;
import com.MicroServices.JobApp.Helper.ErrorResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global exception handler to catch and handle exceptions across the entire application.
 * <p>
 * This class ensures consistent error responses and logs exceptions for diagnostics.
 * It catches specific exceptions (e.g., validation, not found) and provides meaningful
 * responses using a custom {@link ErrorResponse} structure.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles ResourceNotFoundException (e.g., when a database entity is not found).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        log.error("Resource not found: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatusCodeEnum.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCodeEnum.NOT_FOUND.getHttpStatus());
    }

    /**
     * Handles generic exceptions not specifically caught by other handlers.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatusCodeEnum.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later.",
                LocalDateTime.now(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCodeEnum.INTERNAL_SERVER_ERROR.getHttpStatus());
    }

    /**
     * Handles NoResourceFoundException, typically for static resources like files or URLs.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request) {
        log.error("Resource not found: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatusCodeEnum.NOT_FOUND,
                "Requested resource not found.",
                LocalDateTime.now(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCodeEnum.NOT_FOUND.getHttpStatus());
    }

    /**
     * Handles unsupported HTTP method errors (e.g., using POST on a GET-only endpoint).
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        String message = "Request method " + ex.getMethod() + " is not supported on this endpoint. Supported methods: " + Arrays.toString(ex.getSupportedMethods());
        log.warn("Unsupported method: {}", message);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatusCodeEnum.BAD_REQUEST,
                message,
                LocalDateTime.now(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCodeEnum.BAD_REQUEST.getHttpStatus());
    }

    /**
     * Handles invalid arguments passed to methods (e.g., null or invalid format).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        log.warn("Invalid argument: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatusCodeEnum.BAD_REQUEST,
                "Invalid request data provided. Please check the input parameters.",
                LocalDateTime.now(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCodeEnum.BAD_REQUEST.getHttpStatus());
    }

    /**
     * Handles JSON mapping exceptions, typically when the request body is malformed or doesn't match the DTO.
     */
    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<Object> handleJsonMappingException(JsonMappingException ex, HttpServletRequest request) {
        log.error("JSON mapping error: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatusCodeEnum.BAD_REQUEST,
                "Malformed JSON or mapping error occurred. Please check your request payload.",
                LocalDateTime.now(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCodeEnum.BAD_REQUEST.getHttpStatus());
    }

    /**
     * Handles access denied exceptions when a user tries to access unauthorized resources.
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Object> handleAuthorizationDenied(AuthorizationDeniedException ex, HttpServletRequest request) {
        log.error("Authorization denied: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatusCodeEnum.FORBIDDEN,
                "Access Denied: You do not have the necessary permissions to perform this action.",
                LocalDateTime.now(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCodeEnum.FORBIDDEN.getHttpStatus());
    }

    /**
     * Handles validation errors on DTO fields annotated with @Valid.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        String errorMessage = "Validation failed for the following fields: " + String.join(", ", errors);
        log.error("Validation failed: {}", errorMessage);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatusCodeEnum.BAD_REQUEST,
                errorMessage,
                LocalDateTime.now(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCodeEnum.BAD_REQUEST.getHttpStatus());
    }

    /**
     * Catch-all handler for any Throwable not caught by previous handlers.
     * Useful for capturing unexpected serious errors.
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleAllExceptions(Throwable ex, HttpServletRequest request) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatusCodeEnum.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later.",
                LocalDateTime.now(),
                ex.getLocalizedMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCodeEnum.INTERNAL_SERVER_ERROR.getHttpStatus());
    }
}

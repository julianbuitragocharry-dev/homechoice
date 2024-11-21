package com.homechoice.exception;

import com.homechoice.dto.MessageResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Global exception handler for the application.
 * This class handles various exceptions and returns appropriate HTTP responses.
 *
 * @since 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles generic exceptions.
     *
     * @param e the exception
     * @return a ResponseEntity with a generic error message and HTTP status 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleGlobalException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(new MessageResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred!",
                LocalDateTime.now())
            );
    }

    /**
     * Handles BadCredentialsException.
     *
     * @param e the exception
     * @return a ResponseEntity with the exception message and HTTP status 401
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MessageResponse> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
            .body(new MessageResponse(
                HttpStatus.UNAUTHORIZED.value(),
                e.getMessage(),
                LocalDateTime.now())
            );
    }

    /**
     * Handles EntityNotFoundException.
     *
     * @param e the exception
     * @return a ResponseEntity with the exception message and HTTP status 404
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MessageResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
            .body(new MessageResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                LocalDateTime.now())
            );
    }

    /**
     * Handles IOException.
     *
     * @param e the exception
     * @return a ResponseEntity with the exception message and HTTP status 400
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<MessageResponse> handleIOException(IOException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
            .body(new MessageResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                LocalDateTime.now())
            );
    }

    /**
     * Handles IllegalArgumentException.
     *
     * @param e the exception
     * @return a ResponseEntity with the exception message and HTTP status 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageResponse> handleIOException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new MessageResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        LocalDateTime.now())
                );
    }
}

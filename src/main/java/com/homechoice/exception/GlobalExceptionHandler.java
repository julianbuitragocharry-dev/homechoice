package com.homechoice.exception;

import com.homechoice.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public MessageResponse handleGlobalException(Exception ex) {
        return new MessageResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "User updated successfully",
                LocalDateTime.now()
        );
    }
}

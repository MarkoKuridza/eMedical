package org.emedical.advices;

import jakarta.servlet.http.HttpServletRequest;
import org.emedical.exceptions.ExceptionApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    public record ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String message,
            String path
    ) {
    }

    @ExceptionHandler(ExceptionApi.class)
    public ResponseEntity<ErrorResponse> handleException(ExceptionApi ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getStatus().value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(ex.getStatus()).body(error);
    }
}

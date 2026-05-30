package com.aiops.auth_service.exception;
import com.aiops.auth_service.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            UserNotFoundException.class
    )
    public ResponseEntity<ErrorResponse>
    handleUserNotFound(
            UserNotFoundException ex
    ) {

        return ResponseEntity.status(
                HttpStatus.NOT_FOUND
        )
        .body(
                new ErrorResponse(
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }
}
package com.veteroch4k.order.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //400
    @ExceptionHandler({
            HandlerMethodValidationException.class,
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class,

    }
    )
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception e) {

        String errorMessage;

        if (e instanceof HandlerMethodValidationException ex) {
            errorMessage = ex.getAllErrors().getFirst().getDefaultMessage();
        } else if (e instanceof MethodArgumentNotValidException ex) {
            errorMessage = ex.getAllErrors().getFirst().getDefaultMessage();
        } else {
            errorMessage = e.getMessage();
        }

        return buildResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    //404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e) {

        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    //500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {

        log.error("Необработанное исключение: ", e);

        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Произошла внутренняя ошибка сервера.");
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );
        return ResponseEntity.status(status).body(errorResponse);
    }

}

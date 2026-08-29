package com.veteroch4k.employers.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400
    @ExceptionHandler({
            IllegalArgumentException.class,
            HandlerMethodValidationException.class,
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class
    })
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

    // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {

        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {


        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Произошла внутренняя ошибка сервера.");


    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message) {

        ErrorResponse response = new ErrorResponse(

                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );

        return ResponseEntity.status(status).body(response);

    }
}

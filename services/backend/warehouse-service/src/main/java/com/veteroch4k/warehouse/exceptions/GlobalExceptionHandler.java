package com.veteroch4k.warehouse.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // 400
    @ExceptionHandler({
            IllegalAccessException.class,
            HandlerMethodValidationException.class,
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class,
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
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e) {


        return  buildResponse(HttpStatus.NOT_FOUND, e.getMessage());

    }


    // 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {

        log.error("Произошла внутренняя ошибка сервера: {}", e.getMessage(), e);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Произошла внутренняя ошибка сервера");

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

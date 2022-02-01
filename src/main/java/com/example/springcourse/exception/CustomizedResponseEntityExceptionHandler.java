package com.example.springcourse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Objects;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Objects> handleAllExceptions(Exception ex, WebRequest request) {
        return this.exceptionResponseEntity(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Objects> handleUserNotFoundException(Exception ex, WebRequest request) {
        return this.exceptionResponseEntity(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public final ResponseEntity<Objects> handlePostNotFoundException(Exception ex, WebRequest request) {
        return this.exceptionResponseEntity(ex, request, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity exceptionResponseEntity(Exception ex, WebRequest request, HttpStatus status) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity(exceptionResponse, status);
    }
}
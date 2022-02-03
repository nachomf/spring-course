package com.example.springcourse.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions(Exception ex, WebRequest request) {
        return this.exceptionResponseEntity(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity handleUserNotFoundException(Exception ex, WebRequest request) {
        return this.exceptionResponseEntity(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public final ResponseEntity handlePostNotFoundException(Exception ex, WebRequest request) {
        return this.exceptionResponseEntity(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserWithPostException.class)
    public final ResponseEntity handleUserWithPostException(Exception ex, WebRequest request) {
        return this.exceptionResponseEntity(ex, request, HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                "Validation Failed",
                String.join(",", errors)
        );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
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

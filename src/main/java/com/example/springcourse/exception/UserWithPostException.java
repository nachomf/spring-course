package com.example.springcourse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserWithPostException extends RuntimeException {
    public UserWithPostException(){
        super("User cannot be deleted because have posts");
    }
}

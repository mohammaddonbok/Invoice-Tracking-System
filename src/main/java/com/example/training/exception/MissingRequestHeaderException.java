package com.example.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class MissingRequestHeaderException extends RuntimeException{
    public MissingRequestHeaderException(String message){
        super(message);
    }

}

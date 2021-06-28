package com.example.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import springfox.documentation.service.Response;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handelApiRequestException(ApiRequestException e ){
       HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(apiException.getMessage() ,badRequest);
    }

    @ExceptionHandler(value = {MissingRequestHeaderException.class})
    public ResponseEntity<Object> handelMissingHeaderException(MissingRequestHeaderException e){
        HttpStatus unAuthorized = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiException ,unAuthorized);

    }
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handelNotFound(NotFoundException e){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiException ,notFound);

    }
    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> handelAuthenticationException( AuthenticationException e){
        HttpStatus badCredential = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                "Un authorized",
                HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiException ,badCredential);
    }
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handelValidationExceptions(MethodArgumentNotValidException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getFieldError().getDefaultMessage(),
                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException ,badRequest);
    }
}

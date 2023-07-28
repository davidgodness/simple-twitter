package com.davidgodness.simpletwitter.session;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SessionAdvice {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(SessionUnAuthenticatedException.class)
    public String handleUnAuthenticated(SessionUnAuthenticatedException e) {
        return e.getMessage();
    }
}

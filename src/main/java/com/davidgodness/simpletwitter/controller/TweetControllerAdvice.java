package com.davidgodness.simpletwitter.controller;

import com.davidgodness.simpletwitter.exception.TweetNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TweetControllerAdvice {
    @ExceptionHandler(TweetNotFoundException.class)
    public ResponseEntity<String> handleTweetNotFound() {
        return ResponseEntity.notFound().build();
    }
}

package com.davidgodness.simpletwitter.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer id) {
        super("user " + id + " not found");
    }
}

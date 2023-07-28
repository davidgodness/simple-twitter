package com.davidgodness.simpletwitter.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer id) {
        super("user id " + id + " not found");
    }

    public UserNotFoundException(String email) {
        super("user email " + email + " not found");
    }
}

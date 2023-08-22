package com.davidgodness.simpletwitter.exception;

public class UserIdNameExistsException extends Exception {
    public UserIdNameExistsException() {
        super();
    }

    public UserIdNameExistsException(String msg) {
        super(msg);
    }
}

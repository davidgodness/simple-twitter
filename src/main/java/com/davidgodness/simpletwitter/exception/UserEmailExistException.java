package com.davidgodness.simpletwitter.exception;

public class UserEmailExistException extends Exception {
    public UserEmailExistException() {
        super();
    }

    public UserEmailExistException(String msg) {
        super(msg);
    }
}

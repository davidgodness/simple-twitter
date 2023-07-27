package com.davidgodness.simpletwitter.user;

public class UserExistException extends RuntimeException {
    public UserExistException(String idName) {
        super("user:" + idName + " exists, please change idName");
    }
}

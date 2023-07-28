package com.davidgodness.simpletwitter.session;

public class SessionUnAuthenticatedException extends RuntimeException {
    public SessionUnAuthenticatedException() {
        super("login credential not authenticated");
    }
}

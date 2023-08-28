package com.davidgodness.simpletwitter.exception;

public class TweetNotFoundException extends Exception {
    public TweetNotFoundException() {
        super("tweet not found");
    }

    public TweetNotFoundException(String msg) {
        super(msg);
    }
}

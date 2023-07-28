package com.davidgodness.simpletwitter.session;

public record SessionRequestBody(
        String email,
        String password
) {
}

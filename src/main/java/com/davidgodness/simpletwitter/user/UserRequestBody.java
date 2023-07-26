package com.davidgodness.simpletwitter.user;

public record UserRequestBody(
        String email,
        String idName,
        String name,
        String password
) {
}

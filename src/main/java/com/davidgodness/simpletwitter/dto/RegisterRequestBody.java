package com.davidgodness.simpletwitter.dto;

public record RegisterRequestBody(
        String email,
        String idName,
        String name,
        String password
) {
}

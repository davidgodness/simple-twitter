package com.davidgodness.simpletwitter.dto;

public record LoginRequestBody(
        String email,
        String password
) {
}

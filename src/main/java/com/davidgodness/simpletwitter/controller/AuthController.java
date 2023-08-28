package com.davidgodness.simpletwitter.controller;

import com.davidgodness.simpletwitter.dto.LoginRequestBody;
import com.davidgodness.simpletwitter.dto.RegisterRequestBody;
import com.davidgodness.simpletwitter.model.User;
import com.davidgodness.simpletwitter.service.JwtService;
import com.davidgodness.simpletwitter.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {
    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestBody body) {
        Authentication unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(body.email(), body.password());

        Authentication authenticated = authenticationManager.authenticate(unauthenticated);

        userService.login(authenticated);

        return jwtService.token(authenticated);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestBody RegisterRequestBody body) throws Exception {
        User user = userService.register(body);

        return jwtService.token(user);
    }
}

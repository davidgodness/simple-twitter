package com.davidgodness.simpletwitter.session;

import com.davidgodness.simpletwitter.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sessions")
public class SessionController {
    private final UserService userService;

    public SessionController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String login(@RequestBody SessionRequestBody body) {
        return userService.getUserByEmailAndPassword(body.email(), body.password())
                .orElseThrow(SessionUnAuthenticatedException::new).getPassword();
    }
}

package com.davidgodness.simpletwitter.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public void addUser(@RequestBody UserRequestBody body) {
        try {
            userService.registerUser(body);
        } catch (Exception e) {
            return;
        }
    }
}

package com.davidgodness.simpletwitter.user;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getUsers(pageable).getContent());
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserRequestBody body) {
        User newUser = userService.register(body).orElseThrow(() -> new UserExistException(body.idName()));

        URI location = UriComponentsBuilder.fromPath("/users/{id}").build(newUser.getId());

        return ResponseEntity.created(location).body(newUser);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}

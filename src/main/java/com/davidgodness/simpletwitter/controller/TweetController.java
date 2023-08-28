package com.davidgodness.simpletwitter.controller;

import com.davidgodness.simpletwitter.dto.TweetRequestBody;
import com.davidgodness.simpletwitter.model.Tweet;
import com.davidgodness.simpletwitter.model.User;
import com.davidgodness.simpletwitter.service.TweetService;
import com.davidgodness.simpletwitter.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {
    private final UserService userService;

    private final TweetService tweetService;

    public TweetController(UserService userService, TweetService tweetService) {
        this.userService = userService;
        this.tweetService = tweetService;
    }

    @PostMapping
    public ResponseEntity<Tweet> createTweet(@RequestBody TweetRequestBody body, Authentication authentication) {
        User user = (User) userService.loadUserByUsername(authentication.getName());
        Tweet tweet = tweetService.create(body, user);

        return ResponseEntity.created(URI.create("/"+tweet.getId())).body(tweet);
    }

    @GetMapping
    public ResponseEntity<List<Tweet>> getTweets(Pageable pageable, Authentication authentication) {
        User user = (User) userService.loadUserByUsername(authentication.getName());

        return ResponseEntity.ok(tweetService.tweetsByUser(pageable, user).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tweet> getTweet(@PathVariable Integer id) {
        return tweetService.get(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

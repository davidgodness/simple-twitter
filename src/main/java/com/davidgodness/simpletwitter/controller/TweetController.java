package com.davidgodness.simpletwitter.controller;

import com.davidgodness.simpletwitter.dto.TweetRequestBody;
import com.davidgodness.simpletwitter.exception.TweetNotFoundException;
import com.davidgodness.simpletwitter.model.Tweet;
import com.davidgodness.simpletwitter.model.User;
import com.davidgodness.simpletwitter.service.TweetService;
import com.davidgodness.simpletwitter.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

        return ResponseEntity.created(URI.create("/tweets/"+tweet.getId())).body(tweet);
    }

    @GetMapping
    public ResponseEntity<List<Tweet>> getTweets(Pageable pageable, Authentication authentication) {
        User user = (User) userService.loadUserByUsername(authentication.getName());

        return ResponseEntity.ok(tweetService.tweetsByUser(pageable, user).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tweet> getTweet(@PathVariable Integer id) throws TweetNotFoundException {
        return ResponseEntity.ok(tweetService.get(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTweet(@PathVariable Integer id, Authentication authentication) throws Exception {
        User user = (User) userService.loadUserByUsername(authentication.getName());

        tweetService.delete(id, user);
    }

    @PostMapping("/{id}/replies")
    public ResponseEntity<Tweet> reply(@PathVariable Integer id,
                                       @RequestBody TweetRequestBody body,
                                       Authentication authentication) throws TweetNotFoundException {
        User user = (User) userService.loadUserByUsername(authentication.getName());

        Tweet tweet = tweetService.reply(id, body, user);

        return ResponseEntity.created(URI.create("/tweets/" + tweet.getId())).body(tweet);
    }

    @PostMapping("/{id}/forwards")
    public ResponseEntity<Tweet> forward(@PathVariable Integer id, Authentication authentication) throws TweetNotFoundException {
        User user = (User) userService.loadUserByUsername(authentication.getName());

        Tweet tweet = tweetService.forward(id, user);

        return ResponseEntity.created(URI.create("/tweets/" + tweet.getId())).body(tweet);
    }

    @PostMapping("/{id}/references")
    public ResponseEntity<Tweet> reference(@PathVariable Integer id,
                                           @RequestBody TweetRequestBody body,
                                           Authentication authentication) throws TweetNotFoundException {
        User user = (User) userService.loadUserByUsername(authentication.getName());

        Tweet tweet = tweetService.reference(id, body, user);

        return ResponseEntity.created(URI.create("/tweets/" + tweet.getId())).body(tweet);
    }
}

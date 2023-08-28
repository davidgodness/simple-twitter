package com.davidgodness.simpletwitter.service;

import com.davidgodness.simpletwitter.dto.TweetRequestBody;
import com.davidgodness.simpletwitter.model.Tweet;
import com.davidgodness.simpletwitter.model.User;
import com.davidgodness.simpletwitter.repository.TweetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TweetService {
    private final TweetRepository repository;

    public TweetService(TweetRepository repository) {
        this.repository = repository;
    }

    public Tweet create(TweetRequestBody body, User user) {
        Tweet tweet = new Tweet();

        tweet.setContent(body.content());
        tweet.setCreator(user);

        return repository.save(tweet);
    }

    public Page<Tweet> tweetsByUser(Pageable pageable, User user) {
        return repository.findAllByCreatorId(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.DESC, "createdAt"))), user.getId());
    }

    public Optional<Tweet> get(Integer id) {
        return repository.findById(id);
    }
}

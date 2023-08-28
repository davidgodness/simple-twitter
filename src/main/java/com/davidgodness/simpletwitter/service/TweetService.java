package com.davidgodness.simpletwitter.service;

import com.davidgodness.simpletwitter.dto.TweetRequestBody;
import com.davidgodness.simpletwitter.exception.TweetNotFoundException;
import com.davidgodness.simpletwitter.model.Tweet;
import com.davidgodness.simpletwitter.model.User;
import com.davidgodness.simpletwitter.repository.TweetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;


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

    public Tweet get(Integer id) throws TweetNotFoundException {
        return repository.findById(id).orElseThrow(TweetNotFoundException::new);
    }

    public void delete(Integer id, User user) throws Exception {
        Tweet tweet = get(id);

        if (tweet.getCreator() != user) {
            throw new BadCredentialsException("the tweet is not yours");
        }

        repository.delete(tweet);
    }

    public Tweet reply(Integer id, TweetRequestBody body, User user) throws TweetNotFoundException {
        Tweet tweet = get(id);

        Tweet reply = new Tweet();

        reply.setContent(body.content());
        reply.setCreator(user);
        reply.setParentTweet(tweet);

        return repository.save(reply);
    }

    public Tweet forward(Integer id, User user) throws TweetNotFoundException {
        Tweet tweet = get(id);

        Tweet forward = new Tweet();

        forward.setContent("forward");
        forward.setCreator(user);
        forward.setForwardTweet(tweet);

        return repository.save(forward);
    }

    public Tweet reference(Integer id, TweetRequestBody body, User user) throws TweetNotFoundException {
        Tweet tweet = get(id);

        Tweet reference = new Tweet();

        reference.setContent(body.content());
        reference.setCreator(user);
        reference.setReferenceTweet(tweet);

        return repository.save(reference);
    }
}

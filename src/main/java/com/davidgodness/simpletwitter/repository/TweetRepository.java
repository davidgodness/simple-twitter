package com.davidgodness.simpletwitter.repository;

import com.davidgodness.simpletwitter.model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {
    Page<Tweet> findAllByCreatorId(Pageable pageable, Integer creatorId);
}

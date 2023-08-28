package com.davidgodness.simpletwitter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "tweets")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Tweet parentTweet;

    @ManyToOne
    @JoinColumn(name = "forward_tweet_id")
    private Tweet forwardTweet;

    @ManyToOne
    @JoinColumn(name = "reference_tweet_id")
    private Tweet referenceTweet;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

}

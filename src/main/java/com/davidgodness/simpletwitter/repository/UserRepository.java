package com.davidgodness.simpletwitter.repository;

import com.davidgodness.simpletwitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsByIdName(String idName);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}

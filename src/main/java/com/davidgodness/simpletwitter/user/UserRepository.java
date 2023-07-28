package com.davidgodness.simpletwitter.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findFirstByIdName(String idName);

    Optional<User> findFirstByEmailAndPassword(String email, String password);
}

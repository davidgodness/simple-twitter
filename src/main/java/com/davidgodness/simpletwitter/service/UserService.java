package com.davidgodness.simpletwitter.service;

import com.davidgodness.simpletwitter.dto.RegisterRequestBody;
import com.davidgodness.simpletwitter.exception.UserEmailExistException;
import com.davidgodness.simpletwitter.exception.UserIdNameExistsException;
import com.davidgodness.simpletwitter.model.User;
import com.davidgodness.simpletwitter.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User register(RegisterRequestBody body) throws Exception {
        if (idNameExists(body.idName())) {
            throw new UserIdNameExistsException();
        }

        if (emailExists(body.email())) {
            throw new UserEmailExistException();
        }

        return addUser(body);
    }

    public Boolean idNameExists(String idName) {
        return userRepository.existsByIdName(idName);
    }

    public Boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User addUser(RegisterRequestBody body) {
        User newUser = new User();

        newUser.setEmail(body.email());
        newUser.setIdName(body.idName());
        newUser.setName(body.name());
        newUser.setPassword(encoder.encode(body.password()));

        return userRepository.save(newUser);
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.DESC, "createdAt"))
        ));
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("email:" + email + " not found"));
    }
}

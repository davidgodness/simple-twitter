package com.davidgodness.simpletwitter.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> register(UserRequestBody body) {
        User user = null;

        if (!idNameIsPresent(body.idName())) {
            user = addUser(body);
        }

        return Optional.ofNullable(user);
    }

    public Boolean idNameIsPresent(String idName) {
        return userRepository.findFirstByIdName(idName).isPresent();
    }

    public User addUser(UserRequestBody body) {
        User newUser = new User();

        newUser.setEmail(body.email());
        newUser.setIdName(body.idName());
        newUser.setName(body.name());
        newUser.setPassword(body.password());

        long now = new Date().getTime();
        newUser.setCreatedAt(new Timestamp(now));
        newUser.setUpdatedAt(new Timestamp(now));
        newUser.setLastLoginAt(new Timestamp(now));

        return userRepository.save(newUser);
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.DESC, "createdAt"))
        ));
    }

    public void deleteUser(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return;
        }
        throw new UserNotFoundException(id);
    }

    public Optional<User> getUserByEmailAndPassword(String email, String password) {
        return userRepository.findFirstByEmailAndPassword(email, password);
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByIdName(username)
                .orElseThrow(() -> new UserNotFoundException(username + " not found"));
    }
}

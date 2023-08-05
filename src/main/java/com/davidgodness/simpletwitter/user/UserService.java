package com.davidgodness.simpletwitter.user;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
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

    public List<User> getUsers() {
        return userRepository.findAll();
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
}

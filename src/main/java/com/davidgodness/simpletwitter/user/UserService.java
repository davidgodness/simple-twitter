package com.davidgodness.simpletwitter.user;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(UserRequestBody body) {
        if (idNameIsPresent(body.idName())) {
            throw new UserExistException(body.idName());
        }
       return addUser(body);
    }

    public void unregister(Integer id) {
        if (idIsEmpty(id)) {
            throw new UserNotFoundException(id);
        }
        deleteUser(id);
    }

    public Boolean idNameIsPresent(String idName) {
        return userRepository.findFirstByIdName(idName).isPresent();
    }

    public Boolean idIsEmpty(Integer id) {
        return userRepository.findById(id).isEmpty();
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
        userRepository.deleteById(id);
    }
}

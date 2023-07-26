package com.davidgodness.simpletwitter.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final Log logger = LogFactory.getLog(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(UserRequestBody body) throws Exception {
        if (idNameIsExist(body.idName())) {
            logger.warn("idName:" + body.idName() + " has been registered");
            throw new Exception("idName has been registered");
        }
        addUser(body);
    }

    public Boolean idNameIsExist(String idName) {
        return userRepository.findFirstByIdName(idName) != null;
    }

    public void addUser(UserRequestBody body) {
        User newUser = new User();

        newUser.setEmail(body.email());
        newUser.setIdName(body.idName());
        newUser.setName(body.name());
        newUser.setPassword(body.password());

        long now = new Date().getTime();
        newUser.setCreatedAt(new Timestamp(now));
        newUser.setUpdatedAt(new Timestamp(now));
        newUser.setLastLoginAt(new Timestamp(now));

        userRepository.save(newUser);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}

package com.vadim.tkach.gym_tracker.service;

import com.vadim.tkach.gym_tracker.service.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void createUser(User user) {
        log.info("Creating new user");
        log.info("User created: {}", user);
    }

    @Override
    public List<User> getAllUsers() {
        return List.of(
                new User(UUID.randomUUID(),
                        "Vasyl",
                        "vasya@gmail.com",
                        "null"));
    }

    @Override
    public User getUser(UUID id) {
        return new User(UUID.randomUUID(),
                "Vasyl",
                "vasya@gmail.com",
                "null");

    }

    @Override
    public void updateUser(User user) {
        log.info("Updating user with id: {}", user.getId());
        log.info("User created: {}", user);
    }

    @Override
    public void deleteUser(UUID id) {

    }
}

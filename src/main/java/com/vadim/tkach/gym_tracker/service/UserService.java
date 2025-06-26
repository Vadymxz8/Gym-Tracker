package com.vadim.tkach.gym_tracker.service;

import com.vadim.tkach.gym_tracker.service.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(User user);

    List<User> getAllUsers();

    User getUser(UUID id);

    void updateUser(User user);

    void deleteUser(UUID id);
}

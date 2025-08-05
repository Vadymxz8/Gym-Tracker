package com.vadim.tkach.gym_tracker.service.user;

import com.vadim.tkach.gym_tracker.service.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(User user);

    List<User> getAllUsers();

    User getUser(UUID id);

    User getByToken(String token);

    void updateUser(User user);

    void deleteUser(UUID id);

    void completeRegistration(String userId, String password);
}

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

    void completeRegistration(String token, String password);

    User getAuthenticatedUser();

    void changeOwnPassword(String oldPassword, String newPassword);

    void requestPasswordReset(String email);

    User verifyPasswordResetToken(String token);

    void completePasswordReset(String token, String newPassword);
}


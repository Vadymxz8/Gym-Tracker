package com.vadim.tkach.gym_tracker.service;

public interface AuthService {
    String login(String email, String password);
    void changePassword(String email, String newPassword);
    void completePasswordReset(String token, String newPassword);
}


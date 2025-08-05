package com.vadim.tkach.gym_tracker.service.email;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}

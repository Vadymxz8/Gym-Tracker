package com.vadim.tkach.gym_tracker.service;

import com.vadim.tkach.gym_tracker.service.domain.Reminder;

import java.util.List;
import java.util.UUID;

public interface ReminderService {
    void createReminder(Reminder reminder);
    List<Reminder> getAllReminders();
    Reminder getReminder(UUID id);
    void updateReminder(Reminder reminder);
    void deleteReminder(UUID id);
}

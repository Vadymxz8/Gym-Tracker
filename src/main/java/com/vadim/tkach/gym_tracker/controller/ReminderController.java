package com.vadim.tkach.gym_tracker.controller;

import com.vadim.tkach.gym_tracker.service.reminder.ReminderService;
import com.vadim.tkach.gym_tracker.service.model.Reminder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @GetMapping
    public ResponseEntity<List<Reminder>> getAllReminders() {
        List<Reminder> reminders = reminderService.getAllReminders();
        return ResponseEntity.ok(reminders);
    }

    @PostMapping
    public ResponseEntity<Void> createReminder(@RequestBody Reminder reminder) {
        reminderService.createReminder(reminder);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reminder> getReminder(@PathVariable UUID id) {
        Reminder reminder = reminderService.getReminder(id);
        return ResponseEntity.ok(reminder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateReminder(@PathVariable UUID id, @RequestBody Reminder reminder) {
        reminder.setId(id);
        reminderService.updateReminder(reminder);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable UUID id) {
        reminderService.deleteReminder(id);
        return ResponseEntity.noContent().build();
    }
}

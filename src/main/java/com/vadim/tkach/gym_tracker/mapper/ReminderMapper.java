package com.vadim.tkach.gym_tracker.mapper;

import com.vadim.tkach.gym_tracker.repository.entity.ReminderEntity;
import com.vadim.tkach.gym_tracker.service.domain.Reminder;
import org.springframework.stereotype.Component;

@Component
public class ReminderMapper {

    public Reminder toReminder(ReminderEntity entity) {
        if (entity == null) return null;

        return Reminder.builder()
                .id(entity.getId())
                .time(entity.getTime())
                .message(entity.getMessage())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .build();
    }

    public ReminderEntity toReminderEntity(Reminder domain) {
        if (domain == null) return null;

        ReminderEntity entity = new ReminderEntity();
        entity.setId(domain.getId());
        entity.setTime(domain.getTime());
        entity.setMessage(domain.getMessage());


        return entity;
    }
}


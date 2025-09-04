package com.vadim.tkach.gym_tracker.service.reminder;

import com.vadim.tkach.gym_tracker.exception.ReminderNotFoundException;
import com.vadim.tkach.gym_tracker.mapper.ReminderMapper;
import com.vadim.tkach.gym_tracker.repository.ReminderRepository;
import com.vadim.tkach.gym_tracker.repository.UserRepository;
import com.vadim.tkach.gym_tracker.repository.entity.ReminderEntity;
import com.vadim.tkach.gym_tracker.repository.entity.UserEntity;
import com.vadim.tkach.gym_tracker.service.model.Reminder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;
    private final ReminderMapper reminderMapper;
    private final UserRepository userRepository;

    @Override
    public void createReminder(Reminder reminder) {
        ReminderEntity entity = reminderMapper.toReminderEntity(reminder);
        if (reminder.getUserId() != null) {
            UserEntity userEntity = userRepository.findById(reminder.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            entity.setUser(userEntity);
        }
        reminderRepository.save(entity);
    }

    @Override
    public List<Reminder> getAllReminders() {
        return reminderRepository.findAll()
                .stream()
                .map(reminderMapper::toReminder)
                .collect(Collectors.toList());
    }

    @Override
    public Reminder getReminder(UUID id) {
        ReminderEntity entity = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException("Reminder not found with id " + id));
        return reminderMapper.toReminder(entity);
    }

    @Override
    public void updateReminder(Reminder reminder) {
        ReminderEntity entity = reminderRepository.findById(reminder.getId())
                .orElseThrow(() -> new ReminderNotFoundException("Reminder not found with id " + reminder.getId()));

        entity.setTime(reminder.getTime());
        entity.setMessage(reminder.getMessage());
        if (reminder.getUserId() != null) {
            UserEntity userEntity = userRepository.findById(reminder.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            entity.setUser(userEntity);
        }

        reminderRepository.save(entity);
    }

    @Override
    public void deleteReminder(UUID id) {
        if (reminderRepository.existsById(id)) {
            reminderRepository.deleteById(id);
        } else {
            throw new ReminderNotFoundException("Reminder not found with id " + id);
        }
    }
}

package com.vadim.tkach.gym_tracker.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Simple representation of a workout.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workout {
    private UUID id;
    private LocalDate date;
    private String name;
    private String workoutType;
    private String note;
    private UUID userId;
}


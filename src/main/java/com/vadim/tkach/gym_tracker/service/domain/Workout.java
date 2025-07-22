package com.vadim.tkach.gym_tracker.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Workout {
    private UUID id;
    private String name;
    private WorkoutType type;
    private String note;
    private LocalDate date;
    private UUID userId;
    private List<WorkoutExercise> exercises;
}


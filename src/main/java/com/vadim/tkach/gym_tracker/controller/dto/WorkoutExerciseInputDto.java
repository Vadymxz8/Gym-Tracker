package com.vadim.tkach.gym_tracker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class WorkoutExerciseInputDto {
    private int reps;
    private float weight;
    private int sets;
    private String note;
    private UUID exerciseId;
    private UUID workoutId;
}

package com.vadim.tkach.gym_tracker.controller.dto;

import com.vadim.tkach.gym_tracker.service.model.Exercise;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class WorkoutExerciseInputDto {
    private int reps;
    private BigDecimal weight;
    private int sets;
    private String note;
    private UUID exerciseId;
    private UUID workoutId;
    private Exercise exercise;

}

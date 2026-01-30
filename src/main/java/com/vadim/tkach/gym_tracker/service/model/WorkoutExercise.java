package com.vadim.tkach.gym_tracker.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExercise {
    private UUID id;
    private Integer reps;
    private BigDecimal weight;
    private Integer sets;
    private String note;
    private UUID exerciseId;
    private UUID workoutId;
    private Exercise exercise;

}



package com.vadim.tkach.gym_tracker.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class WorkoutExerciseInputDto {

    @Positive
    private int reps;

    @Positive
    private int sets;

    @NotNull
    private BigDecimal weight;

    private String note;

    @NotNull
    private UUID exerciseId;
}

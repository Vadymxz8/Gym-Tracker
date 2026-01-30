package com.vadim.tkach.gym_tracker.controller.dto;

import com.vadim.tkach.gym_tracker.service.model.Exercise;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class WorkoutExerciseInputDto {
    private int reps;
    private BigDecimal weight;
    private int sets;
    private String note;
    @NotNull
    private UUID userId;

    @NotEmpty
    private List<@Valid WorkoutExerciseInputDto> exercises;

}

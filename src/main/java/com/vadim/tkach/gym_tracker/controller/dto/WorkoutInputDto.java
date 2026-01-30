package com.vadim.tkach.gym_tracker.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class WorkoutInputDto {

    @NotNull
    private LocalDate date;

    @NotNull
    private String name;

    @NotNull
    private String type;

    private String note;

    @NotNull
    private UUID userId;

    @NotEmpty
    private List<@Valid WorkoutExerciseInputDto> exercises;
}

package com.vadim.tkach.gym_tracker.controller.dto;

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
    private LocalDate date;
    private String name;
    private String type;
    private String note;
    private UUID userId;
    private List<WorkoutExerciseInputDto> exercises;
}

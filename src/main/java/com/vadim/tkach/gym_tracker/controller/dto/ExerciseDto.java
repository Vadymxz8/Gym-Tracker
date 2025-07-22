package com.vadim.tkach.gym_tracker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class ExerciseDto {
    private UUID id;
    private String name;
    private String type;
    private String muscleGroup;
}

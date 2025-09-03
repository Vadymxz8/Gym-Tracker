package com.vadim.tkach.gym_tracker.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Simple representation of an exercise.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    private UUID id;
    private String name;
    private String type;
    private String muscleGroup;
}


package com.vadim.tkach.gym_tracker.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    private UUID id;
    private String name;
    private String type;
    private String muscleGroup;
}


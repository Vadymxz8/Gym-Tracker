package com.vadim.tkach.gym_tracker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ProgressDto {
    private int days;
    private double volumeChangePct;
    private double strengthChangePct;
    private int sessions;
    private double sessionsPerWeek;
    private String feedback;
}


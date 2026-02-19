package com.vadim.tkach.gym_tracker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class BestSetPointDto {
    private LocalDate date;
    private UUID exerciseId;
    private String exerciseName;
    private BigDecimal weight;
    private Integer reps;
    private BigDecimal score;
}

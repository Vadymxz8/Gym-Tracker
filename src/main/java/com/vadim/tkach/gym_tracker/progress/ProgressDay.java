package com.vadim.tkach.gym_tracker.progress;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ProgressDay {
    private LocalDate date;
    private double volume;
    private int sessions;
    private double est1rmBest;
}


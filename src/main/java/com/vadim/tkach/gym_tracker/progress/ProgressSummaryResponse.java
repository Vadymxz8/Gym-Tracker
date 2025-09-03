package com.vadim.tkach.gym_tracker.progress;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProgressSummaryResponse {
    private List<ProgressDay> days;
    private Totals totals;
    private Trend trend;
    private boolean stagnation;
}

@Data
@AllArgsConstructor
class Totals {
    private double volume;
    private int sessions;
}

@Data
@AllArgsConstructor
class Trend {
    private double est1rmChangePct;
    private double volumeChangePct;
}


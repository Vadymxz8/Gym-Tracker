package com.vadim.tkach.gym_tracker.progress;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final ProgressRepository repository;

    public ProgressSummaryResponse getSummary(UUID userId, String range) {
        int days = "month".equalsIgnoreCase(range) ? 30 : 7;
        LocalDate to = LocalDate.now();
        LocalDate from = to.minusDays(days - 1);

        List<ProgressDay> current = fillDays(repository.dailySummary(userId, from, to), from, to);
        Totals currentTotals = aggregate(current);

        LocalDate prevTo = from.minusDays(1);
        LocalDate prevFrom = prevTo.minusDays(days - 1);
        List<ProgressDay> prev = repository.dailySummary(userId, prevFrom, prevTo);
        Totals prevTotals = aggregate(prev);

        double currentEst = current.stream().mapToDouble(ProgressDay::getEst1rmBest).max().orElse(0d);
        double prevEst = prev.stream().mapToDouble(ProgressDay::getEst1rmBest).max().orElse(0d);
        double estChange = ProgressCalculator.changePct(currentEst, prevEst);
        double volumeChange = ProgressCalculator.changePct(currentTotals.getVolume(), prevTotals.getVolume());
        boolean stagnation = ProgressCalculator.isStagnating(estChange, volumeChange);

        Trend trend = new Trend(estChange, volumeChange);
        return new ProgressSummaryResponse(current, currentTotals, trend, stagnation);
    }

    public List<ExerciseProgressDto> getExerciseProgress(UUID userId, UUID exerciseId, String range) {
        int days = "month".equalsIgnoreCase(range) ? 30 : 7;
        LocalDate to = LocalDate.now();
        LocalDate from = to.minusDays(days - 1);
        return repository.exerciseProgress(userId, exerciseId, from, to);
    }

    public List<SessionDto> getSessions(UUID userId, LocalDate cursor, int limit) {
        return repository.sessions(userId, cursor, limit);
    }

    private List<ProgressDay> fillDays(List<ProgressDay> days, LocalDate from, LocalDate to) {
        Map<LocalDate, ProgressDay> map = days.stream()
                .collect(Collectors.toMap(ProgressDay::getDate, d -> d));
        List<ProgressDay> result = new ArrayList<>();
        for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {
            result.add(map.getOrDefault(date, new ProgressDay(date, 0d, 0, 0d)));
        }
        return result;
    }

    private Totals aggregate(List<ProgressDay> days) {
        double volume = days.stream().mapToDouble(ProgressDay::getVolume).sum();
        int sessions = days.stream().mapToInt(ProgressDay::getSessions).sum();
        return new Totals(volume, sessions);
    }
}


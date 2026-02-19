package com.vadim.tkach.gym_tracker.service.progress;

import com.vadim.tkach.gym_tracker.controller.dto.BestSetPointDto;
import com.vadim.tkach.gym_tracker.controller.dto.FrequencyPointDto;
import com.vadim.tkach.gym_tracker.controller.dto.ProgressDto;
import com.vadim.tkach.gym_tracker.controller.dto.VolumePointDto;
import com.vadim.tkach.gym_tracker.repository.WorkoutRepository;
import com.vadim.tkach.gym_tracker.repository.entity.ExerciseEntity;
import com.vadim.tkach.gym_tracker.repository.entity.WorkoutEntity;
import com.vadim.tkach.gym_tracker.repository.entity.WorkoutExerciseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

    private final WorkoutRepository workoutRepository;

    @Override
    @Transactional(readOnly = true)
    public ProgressDto getProgress(UUID userId, int days) {
        validateDays(days);

        LocalDate to = LocalDate.now();
        LocalDate from = to.minusDays(days - 1L);

        LocalDate prevTo = from.minusDays(1);
        LocalDate prevFrom = prevTo.minusDays(days - 1L);

        List<WorkoutEntity> nowWorkouts =
                workoutRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId, from, to);
        List<WorkoutEntity> prevWorkouts =
                workoutRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId, prevFrom, prevTo);

        // Volume (current & previous)
        BigDecimal volNow = totalVolume(nowWorkouts);
        BigDecimal volPrev = totalVolume(prevWorkouts);
        double volumeChangePct = round2(pct(volPrev, volNow));

        // Strength delta (best score per exercise: max(weight * reps) across period)
        Map<UUID, BigDecimal> bestNow = bestScorePerExercise(nowWorkouts);
        Map<UUID, BigDecimal> bestPrev = bestScorePerExercise(prevWorkouts);
        double strengthChangePct = round2(averagePct(bestPrev, bestNow));

        // Consistency
        int sessions = nowWorkouts.size();
        double sessionsPerWeek = round2(sessions / (days / 7.0));

        String feedback = makeFeedback(strengthChangePct, volumeChangePct, sessions);

        return ProgressDto.builder()
                .days(days)
                .volumeChangePct(volumeChangePct)
                .strengthChangePct(strengthChangePct)
                .sessions(sessions)
                .sessionsPerWeek(sessionsPerWeek)
                .feedback(feedback)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VolumePointDto> getVolumeSeries(UUID userId, int days) {
        validateDays(days);
        LocalDate to = LocalDate.now();
        LocalDate from = to.minusDays(days - 1L);

        List<WorkoutEntity> workouts =
                workoutRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId, from, to);

        return workouts.stream()
                .map(this::toVolumePoint)
                .sorted(Comparator.comparing(VolumePointDto::getDate))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BestSetPointDto> getBestSetSeries(UUID userId, int days) {
        validateDays(days);
        LocalDate to = LocalDate.now();
        LocalDate from = to.minusDays(days - 1L);

        List<WorkoutEntity> workouts =
                workoutRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId, from, to);

        // key: date + exerciseId  -> best score dto
        Map<String, BestSetPointDto> bestByDateAndExercise = new HashMap<>();

        for (WorkoutEntity w : workouts) {
            LocalDate date = w.getDate();
            if (w.getWorkoutExercises() == null) continue;

            for (WorkoutExerciseEntity we : w.getWorkoutExercises()) {
                ExerciseEntity e = we.getExercise();
                if (e == null) continue;

                UUID exerciseId = e.getId();
                String exerciseName = e.getName();

                BigDecimal weight = safe(we.getWeight());
                int reps = safeInt(we.getReps());
                BigDecimal score = weight.multiply(BigDecimal.valueOf(reps));

                String key = date + ":" + exerciseId;
                BestSetPointDto prev = bestByDateAndExercise.get(key);

                if (prev == null || score.compareTo(prev.getScore()) > 0) {
                    bestByDateAndExercise.put(key, BestSetPointDto.builder()
                            .date(date)
                            .exerciseId(exerciseId)
                            .exerciseName(exerciseName)
                            .weight(weight)
                            .reps(reps)
                            .score(score)
                            .build());
                }
            }
        }

        return bestByDateAndExercise.values().stream()
                .sorted(Comparator.comparing(BestSetPointDto::getDate)
                        .thenComparing(BestSetPointDto::getExerciseName, Comparator.nullsLast(String::compareTo)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FrequencyPointDto> getFrequencySeries(UUID userId, int days) {
        validateDays(days);
        LocalDate to = LocalDate.now();
        LocalDate from = to.minusDays(days - 1L);

        List<WorkoutEntity> workouts =
                workoutRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId, from, to);

        Map<LocalDate, Integer> perWeek = new HashMap<>();
        for (WorkoutEntity w : workouts) {
            LocalDate weekStart = weekStartMonday(w.getDate());
            perWeek.merge(weekStart, 1, Integer::sum);
        }

        return perWeek.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> FrequencyPointDto.builder()
                        .weekStart(e.getKey())
                        .sessions(e.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    // ===== Helpers =====
    private String makeFeedback(double strengthDelta, double volumeDelta, int sessions) {
        // якщо взагалі не тренувався у періоді
        if (sessions == 0) {
            return "Немає тренувань у вибраному періоді. Додай 3–4 сесії на тиждень і онови дашборд.";
        }

        // сильний прогрес у силі
        if (strengthDelta >= 10.0) {
            return "Ти ростеш, так тримати! (+" + round2(strengthDelta) + "% сили)";
        }

        // працюєш більше, але сила не росте
        if (strengthDelta < 2.0 && volumeDelta > 0.0) {
            return "Ти працюєш, але не прогресуєш у силі. Подумай про важчі ваги або зменшення пампу.";
        }

        // все падає — переглянути відпочинок/харчування/програму
        if (strengthDelta < 0.0 && volumeDelta < 0.0) {
            return "Ти застиг. Переглянь відпочинок, харчування або програму.";
        }

        // дефолт
        return "Тримай темп!";
    }

    private void validateDays(int days) {
        if (days != 7 && days != 30 && days != 90 && days != 180) {
            throw new IllegalArgumentException("days must be one of 7, 30, 90, 180");
        }
    }

    private VolumePointDto toVolumePoint(WorkoutEntity w) {
        BigDecimal volume = BigDecimal.ZERO;
        if (w.getWorkoutExercises() != null) {
            for (WorkoutExerciseEntity we : w.getWorkoutExercises()) {
                BigDecimal weight = safe(we.getWeight());
                int reps = safeInt(we.getReps());
                int sets = safeInt(we.getSets());
                BigDecimal setVol = weight
                        .multiply(BigDecimal.valueOf(reps))
                        .multiply(BigDecimal.valueOf(sets));
                volume = volume.add(setVol);
            }
        }
        return VolumePointDto.builder()
                .workoutId(w.getId())
                .date(w.getDate())
                .volume(volume)
                .build();
    }

    private BigDecimal totalVolume(List<WorkoutEntity> workouts) {
        BigDecimal total = BigDecimal.ZERO;
        for (WorkoutEntity w : workouts) {
            total = total.add(toVolumePoint(w).getVolume());
        }
        return total;
    }

    private Map<UUID, BigDecimal> bestScorePerExercise(List<WorkoutEntity> workouts) {
        Map<UUID, BigDecimal> best = new HashMap<>();
        for (WorkoutEntity w : workouts) {
            if (w.getWorkoutExercises() == null) continue;
            for (WorkoutExerciseEntity we : w.getWorkoutExercises()) {
                ExerciseEntity e = we.getExercise();
                if (e == null) continue;
                UUID exId = e.getId();

                BigDecimal score = safe(we.getWeight())
                        .multiply(BigDecimal.valueOf(safeInt(we.getReps())));

                best.merge(exId, score, (a, b) -> a.max(b));
            }
        }
        return best;
    }

    private double averagePct(Map<UUID, BigDecimal> prev, Map<UUID, BigDecimal> now) {
        Set<UUID> common = new HashSet<>(prev.keySet());
        common.retainAll(now.keySet());
        if (common.isEmpty()) {
            return now.isEmpty() ? 0.0 : 100.0;
        }
        double sum = 0.0;
        for (UUID id : common) {
            sum += pct(prev.get(id), now.get(id));
        }
        return sum / common.size();
    }

    private double pct(BigDecimal prev, BigDecimal now) {
        BigDecimal p = safe(prev);
        BigDecimal n = safe(now);
        if (p.compareTo(BigDecimal.ZERO) == 0) {
            return n.compareTo(BigDecimal.ZERO) == 0 ? 0.0 : 100.0;
        }
        return n.subtract(p)
                .divide(p, 6, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    private LocalDate weekStartMonday(LocalDate d) {
        DayOfWeek dow = d.getDayOfWeek();
        int shift = dow.getValue() - DayOfWeek.MONDAY.getValue(); // MONDAY=1...SUNDAY=7
        return d.minusDays(shift);
    }

    private BigDecimal safe(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }

    private int safeInt(Integer v) {
        return v == null ? 0 : v;
    }
}


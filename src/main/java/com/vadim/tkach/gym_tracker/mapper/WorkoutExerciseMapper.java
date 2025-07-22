package com.vadim.tkach.gym_tracker.mapper;

import com.vadim.tkach.gym_tracker.repository.entity.WorkoutExerciseEntity;
import com.vadim.tkach.gym_tracker.service.domain.WorkoutExercise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkoutExerciseMapper {

    public WorkoutExercise toWorkoutExercise(WorkoutExerciseEntity entity) {
        if (entity == null) return null;

        return WorkoutExercise.builder()
                .id(entity.getId())
                .reps(entity.getReps())
                .weight(entity.getWeight() != null ? entity.getWeight().floatValue() : null)
                .sets(entity.getSets())
                .note(entity.getNote())
                .exerciseId(entity.getExercise() != null ? entity.getExercise().getId() : null)
                .workoutId(entity.getWorkout() != null ? entity.getWorkout().getId() : null)
                .build();
    }

    public WorkoutExerciseEntity toWorkoutExerciseEntity(WorkoutExercise domain) {
        if (domain == null) return null;

        WorkoutExerciseEntity entity = new WorkoutExerciseEntity();
        entity.setId(domain.getId());
        entity.setReps(domain.getReps());
        entity.setWeight(domain.getWeight() != null ? domain.getWeight().doubleValue() : null);
        entity.setSets(domain.getSets());
        entity.setNote(domain.getNote());

        return entity;
    }
}

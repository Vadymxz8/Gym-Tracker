package com.vadim.tkach.gym_tracker.mapper;

import com.vadim.tkach.gym_tracker.repository.entity.WorkoutEntity;
import com.vadim.tkach.gym_tracker.service.model.Workout;
import com.vadim.tkach.gym_tracker.service.model.WorkoutType;
import com.vadim.tkach.gym_tracker.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WorkoutMapper {

    private final WorkoutExerciseMapper workoutExerciseMapper;

    public Workout toWorkout(WorkoutEntity entity) {
        if (entity == null) return null;

        return Workout.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .name(entity.getName())
                .note(entity.getNote())
                .type(entity.getWorkoutType())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .exercises(entity.getWorkoutExercises() != null
                        ? entity.getWorkoutExercises().stream()
                        .map(workoutExerciseMapper::toWorkoutExercise)
                        .collect(Collectors.toList())
                        : null
                )
                .build();
    }

    public WorkoutEntity toWorkoutEntity(Workout workout, UserEntity userEntity) {
        if (workout == null) return null;

        WorkoutEntity entity = new WorkoutEntity();
        entity.setId(workout.getId());
        entity.setDate(workout.getDate());
        entity.setName(workout.getName());
        entity.setNote(workout.getNote());
        entity.setWorkoutType(workout.getType());
        entity.setUser(userEntity);

        return entity;
    }

    private WorkoutType parseWorkoutType(String value) {
        try {
            return WorkoutType.valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }
}
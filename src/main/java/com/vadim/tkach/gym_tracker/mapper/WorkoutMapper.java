package com.vadim.tkach.gym_tracker.mapper;

import com.vadim.tkach.gym_tracker.repository.entity.WorkoutEntity;
import com.vadim.tkach.gym_tracker.service.model.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkoutMapper {

    private final WorkoutExerciseMapper workoutExerciseMapper;

    public WorkoutEntity toWorkoutEntity(Workout model, com.vadim.tkach.gym_tracker.repository.entity.UserEntity user) {
        WorkoutEntity entity = new WorkoutEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setDate(model.getDate());
        entity.setNote(model.getNote());
        entity.setWorkoutType(model.getType());
        entity.setUser(user);
        return entity;
    }

    public Workout toWorkout(WorkoutEntity entity) {
        return Workout.builder()
                .id(entity.getId())
                .name(entity.getName())
                .date(entity.getDate())
                .note(entity.getNote())
                .type(entity.getWorkoutType())
                .userId(entity.getUser().getId())
                .exercises(
                        entity.getExercises().stream()
                                .map(workoutExerciseMapper::toWorkoutExercise)
                                .toList()
                )
                .build();
    }
}

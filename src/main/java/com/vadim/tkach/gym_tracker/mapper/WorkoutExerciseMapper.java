package com.vadim.tkach.gym_tracker.mapper;

import com.vadim.tkach.gym_tracker.controller.dto.WorkoutExerciseInputDto;
import com.vadim.tkach.gym_tracker.repository.entity.WorkoutExerciseEntity;
import com.vadim.tkach.gym_tracker.service.model.WorkoutExercise;
import org.springframework.stereotype.Component;

@Component
public class WorkoutExerciseMapper {

    public WorkoutExercise toWorkoutExercise(WorkoutExerciseInputDto dto) {
        if (dto == null) return null;

        return WorkoutExercise.builder()
                .exerciseId(dto.getExerciseId())
                .sets(dto.getSets())
                .reps(dto.getReps())
                .weight(dto.getWeight())
                .note(dto.getNote())
                .workoutId(dto.getWorkoutId())
                .build();
    }

    public WorkoutExerciseInputDto toWorkoutExerciseInputDto(WorkoutExercise exercise) {
        if (exercise == null) return null;

        return WorkoutExerciseInputDto.builder()
                .exerciseId(exercise.getExerciseId())
                .sets(exercise.getSets())
                .reps(exercise.getReps())
                .weight(exercise.getWeight())
                .note(exercise.getNote())
                .workoutId(exercise.getWorkoutId())
                .build();
    }

    public WorkoutExercise toWorkoutExercise(WorkoutExerciseEntity entity) {
        if (entity == null) return null;

        return WorkoutExercise.builder()
                .id(entity.getId())
                .exerciseId(entity.getExercise().getId())
                .sets(entity.getSets())
                .reps(entity.getReps())
                .weight(entity.getWeight())
                .note(entity.getNote())
                .workoutId(entity.getWorkout() != null ? entity.getWorkout().getId() : null)
                .build();
    }

    public WorkoutExerciseEntity toWorkoutExerciseEntity(WorkoutExercise exercise) {
        if (exercise == null) return null;

        WorkoutExerciseEntity entity = new WorkoutExerciseEntity();
        entity.setId(exercise.getId());
        entity.setSets(exercise.getSets());
        entity.setReps(exercise.getReps());
        entity.setWeight(exercise.getWeight());
        entity.setNote(exercise.getNote());
        return entity;
    }
}

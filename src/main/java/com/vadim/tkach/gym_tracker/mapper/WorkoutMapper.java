package com.vadim.tkach.gym_tracker.mapper;

import com.vadim.tkach.gym_tracker.controller.dto.WorkoutInputDto;
import com.vadim.tkach.gym_tracker.repository.entity.UserEntity;
import com.vadim.tkach.gym_tracker.repository.entity.WorkoutEntity;
import com.vadim.tkach.gym_tracker.service.model.Workout;
import com.vadim.tkach.gym_tracker.service.model.WorkoutExercise;
import com.vadim.tkach.gym_tracker.service.model.WorkoutType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkoutMapper {

    private final WorkoutExerciseMapper workoutExerciseMapper;

    // ENTITY -> MODEL
    public Workout toWorkout(WorkoutEntity entity) {
        return Workout.builder()
                .id(entity.getId())
                .name(entity.getName())
                .date(entity.getDate())
                .note(entity.getNote())
                .type(entity.getWorkoutType())
                .userId(entity.getUser().getId())
                .exercises(
                        entity.getExercises() == null ? List.of() :
                                entity.getExercises().stream()
                                        .map(workoutExerciseMapper::toWorkoutExercise)
                                        .toList()
                )
                .build();
    }

    // DTO -> MODEL
    public Workout toWorkout(WorkoutInputDto dto) {
        List<WorkoutExercise> exercises =
                dto.getExercises() == null ? List.of() :
                        dto.getExercises().stream()
                                .map(workoutExerciseMapper::toWorkoutExercise)
                                .toList();

        return Workout.builder()
                .name(dto.getName())
                .date(dto.getDate())
                .note(dto.getNote())
                .type(WorkoutType.valueOf(dto.getType())) // ✅ FIX
                .userId(dto.getUserId())
                .exercises(exercises)
                .build();
    }

    // MODEL -> ENTITY
    public WorkoutEntity toWorkoutEntity(Workout model, UserEntity user) {
        WorkoutEntity entity = new WorkoutEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setDate(model.getDate());
        entity.setNote(model.getNote());
        entity.setWorkoutType(model.getType());
        entity.setUser(user);
        return entity;
    }
}

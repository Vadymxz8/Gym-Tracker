package com.vadim.tkach.gym_tracker.mapper;

import com.vadim.tkach.gym_tracker.controller.dto.WorkoutExerciseInputDto;
import com.vadim.tkach.gym_tracker.repository.entity.WorkoutExerciseEntity;
import com.vadim.tkach.gym_tracker.service.model.WorkoutExercise;
import org.springframework.stereotype.Component;

@Component
public class WorkoutExerciseMapper {

    // MODEL -> ENTITY
    public WorkoutExerciseEntity toWorkoutExerciseEntity(WorkoutExercise model) {
        if (model == null) {
            return null;
        }

        WorkoutExerciseEntity entity = new WorkoutExerciseEntity();
        entity.setReps(model.getReps());
        entity.setSets(model.getSets());
        entity.setWeight(model.getWeight());
        entity.setNote(model.getNote());
        return entity;
    }

    // ENTITY -> MODEL
    public WorkoutExercise toWorkoutExercise(WorkoutExerciseEntity entity) {
        if (entity == null) {
            return null;
        }

        return WorkoutExercise.builder()
                .id(entity.getId())
                .reps(entity.getReps())
                .sets(entity.getSets())
                .weight(entity.getWeight())
                .note(entity.getNote())
                .exerciseId(
                        entity.getExercise() != null
                                ? entity.getExercise().getId()
                                : null
                )
                .build();
    }

    // DTO -> MODEL
    public WorkoutExercise toWorkoutExercise(WorkoutExerciseInputDto dto) {
        if (dto == null) {
            return null;
        }

        return WorkoutExercise.builder()
                .reps(dto.getReps())
                .sets(dto.getSets())
                .weight(dto.getWeight())
                .note(dto.getNote())
                .exerciseId(dto.getExerciseId())
                .build();
    }
}

package com.vadim.tkach.gym_tracker.mapper;

import com.vadim.tkach.gym_tracker.controller.dto.WorkoutExerciseInputDto;
import com.vadim.tkach.gym_tracker.repository.entity.WorkoutExerciseEntity;
import com.vadim.tkach.gym_tracker.service.model.WorkoutExercise;
import org.springframework.stereotype.Component;

@Component
public class WorkoutExerciseMapper {

    public WorkoutExerciseEntity toWorkoutExerciseEntity(WorkoutExercise model) {
        WorkoutExerciseEntity entity = new WorkoutExerciseEntity();
        entity.setReps(model.getReps());
        entity.setSets(model.getSets());
        entity.setWeight(model.getWeight());
        entity.setNote(model.getNote());
        return entity;
    }

    public WorkoutExercise toWorkoutExercise(WorkoutExerciseEntity entity) {
        return WorkoutExercise.builder()
                .id(entity.getId())
                .reps(entity.getReps())
                .sets(entity.getSets())
                .weight(entity.getWeight())
                .note(entity.getNote())
                .exerciseId(entity.getExercise().getId())
                .build();
    }

    public WorkoutExercise toWorkoutExercise(WorkoutExerciseInputDto dto) {
        return WorkoutExercise.builder()
                .reps(dto.getReps())
                .sets(dto.getSets())
                .weight(dto.getWeight())
                .note(dto.getNote())
                .exerciseId(dto.getExerciseId())
                .build();
    }
}

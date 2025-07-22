package com.vadim.tkach.gym_tracker.mapper;

import com.vadim.tkach.gym_tracker.repository.entity.ExerciseEntity;
import com.vadim.tkach.gym_tracker.service.domain.Exercise;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper {

    public Exercise toExercise(ExerciseEntity entity) {
        if (entity == null) return null;

        return Exercise.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .muscleGroup(entity.getMuscleGroup())
                .build();
    }

    public ExerciseEntity toExerciseEntity(Exercise domain) {
        if (domain == null) return null;

        ExerciseEntity entity = new ExerciseEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setType(domain.getType());
        entity.setMuscleGroup(domain.getMuscleGroup());

        return entity;
    }
}

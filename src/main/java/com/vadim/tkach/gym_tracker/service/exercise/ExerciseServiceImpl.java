package com.vadim.tkach.gym_tracker.service.exercise;

import com.vadim.tkach.gym_tracker.exception.ExerciseNotFoundException;
import com.vadim.tkach.gym_tracker.mapper.ExerciseMapper;
import com.vadim.tkach.gym_tracker.repository.ExerciseRepository;
import com.vadim.tkach.gym_tracker.repository.entity.ExerciseEntity;
import com.vadim.tkach.gym_tracker.service.model.Exercise;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    @Override
    public Exercise createExercise(Exercise exercise) {
        ExerciseEntity saved = exerciseRepository.save(
                exerciseMapper.toExerciseEntity(exercise)
        );
        return exerciseMapper.toExercise(saved);
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll()
                .stream()
                .map(exerciseMapper::toExercise)
                .collect(Collectors.toList());
    }

    @Override
    public Exercise getExercise(UUID id) {
        ExerciseEntity entity = exerciseRepository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise not found with id " + id));
        return exerciseMapper.toExercise(entity);
    }

    @Override
    public void updateExercise(Exercise exercise) {
        ExerciseEntity entity = exerciseRepository.findById(exercise.getId())
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise not found with id " + exercise.getId()));

        entity.setName(exercise.getName());
        entity.setType(exercise.getType());
        entity.setMuscleGroup(exercise.getMuscleGroup());

        exerciseRepository.save(entity);
    }

    @Override
    public void deleteExercise(UUID id) {
        if (exerciseRepository.existsById(id)) {
            exerciseRepository.deleteById(id);
        } else {
            throw new ExerciseNotFoundException("Exercise not found with id " + id);
        }
    }
}


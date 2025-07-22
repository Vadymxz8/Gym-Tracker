package com.vadim.tkach.gym_tracker.service;

import com.vadim.tkach.gym_tracker.service.domain.Exercise;

import java.util.List;
import java.util.UUID;

public interface ExerciseService {
    void createExercise(Exercise exercise);
    List<Exercise> getAllExercises();
    Exercise getExercise(UUID id);
    void updateExercise(Exercise exercise);
    void deleteExercise(UUID id);
}


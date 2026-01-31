package com.vadim.tkach.gym_tracker.service.exercise;

import com.vadim.tkach.gym_tracker.service.model.Exercise;

import java.util.List;
import java.util.UUID;

public interface ExerciseService {
    Exercise createExercise(Exercise exercise);
    List<Exercise> getAllExercises();
    Exercise getExercise(UUID id);
    void updateExercise(Exercise exercise);
    void deleteExercise(UUID id);
}


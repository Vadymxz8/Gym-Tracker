package com.vadim.tkach.gym_tracker.service;

import com.vadim.tkach.gym_tracker.service.domain.WorkoutExercise;

import java.util.List;
import java.util.UUID;

public interface WorkoutExerciseService {
    void addWorkoutExercise(WorkoutExercise workoutExercise);
    List<WorkoutExercise> getAllWorkoutExercises();
    WorkoutExercise getWorkoutExercise(UUID id);
    void deleteWorkoutExercise(UUID id);
}


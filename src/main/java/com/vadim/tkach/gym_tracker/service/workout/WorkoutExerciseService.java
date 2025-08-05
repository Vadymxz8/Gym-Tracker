package com.vadim.tkach.gym_tracker.service.workout;

import com.vadim.tkach.gym_tracker.service.model.WorkoutExercise;

import java.util.List;
import java.util.UUID;

public interface WorkoutExerciseService {
    void addWorkoutExercise(WorkoutExercise workoutExercise);
    List<WorkoutExercise> getAllWorkoutExercises();
    WorkoutExercise getWorkoutExercise(UUID id);
    void deleteWorkoutExercise(UUID id);
}


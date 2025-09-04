package com.vadim.tkach.gym_tracker.service.workout;

import com.vadim.tkach.gym_tracker.service.model.Workout;

import java.util.List;
import java.util.UUID;

public interface WorkoutService {
    void createWorkout(Workout workout);
    void updateWorkout(Workout workout);
    void deleteWorkout(UUID id);
    List<Workout> getAllWorkouts();
    Workout getWorkoutById(UUID id);
    List<Workout> getWorkoutsByUserId(UUID userId);
}


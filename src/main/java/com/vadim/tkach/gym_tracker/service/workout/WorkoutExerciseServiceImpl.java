package com.vadim.tkach.gym_tracker.service.workout;

import com.vadim.tkach.gym_tracker.mapper.WorkoutExerciseMapper;
import com.vadim.tkach.gym_tracker.repository.ExerciseRepository;
import com.vadim.tkach.gym_tracker.repository.WorkoutExerciseRepository;
import com.vadim.tkach.gym_tracker.repository.WorkoutRepository;
import com.vadim.tkach.gym_tracker.repository.entity.ExerciseEntity;
import com.vadim.tkach.gym_tracker.repository.entity.WorkoutEntity;
import com.vadim.tkach.gym_tracker.repository.entity.WorkoutExerciseEntity;
import com.vadim.tkach.gym_tracker.service.model.WorkoutExercise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutExerciseServiceImpl implements WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutExerciseMapper workoutExerciseMapper;

    @Override
    public void addWorkoutExercise(WorkoutExercise workoutExercise) {
        WorkoutExerciseEntity entity = workoutExerciseMapper.toWorkoutExerciseEntity(workoutExercise);

        ExerciseEntity exercise = exerciseRepository.findById(workoutExercise.getExerciseId())
                .orElseThrow(() -> new RuntimeException("Exercise not found with ID: " + workoutExercise.getExerciseId()));

        WorkoutEntity workout = workoutRepository.findById(workoutExercise.getWorkoutId())
                .orElseThrow(() -> new RuntimeException("Workout not found with ID: " + workoutExercise.getWorkoutId()));

        entity.setExercise(exercise);
        entity.setWorkout(workout);
        workoutExerciseRepository.save(entity);
    }

    @Override
    public List<WorkoutExercise> getAllWorkoutExercises() {
        return workoutExerciseRepository.findAll().stream()
                .map(workoutExerciseMapper::toWorkoutExercise)
                .collect(Collectors.toList());
    }

    @Override
    public WorkoutExercise getWorkoutExercise(UUID id) {
        return workoutExerciseRepository.findById(id)
                .map(workoutExerciseMapper::toWorkoutExercise)
                .orElseThrow(() -> new RuntimeException("WorkoutExercise not found with ID: " + id));
    }

    @Override
    public void deleteWorkoutExercise(UUID id) {
        workoutExerciseRepository.deleteById(id);
    }
}

package com.vadim.tkach.gym_tracker.service.workout;

import com.vadim.tkach.gym_tracker.exception.UserNotFoundException;
import com.vadim.tkach.gym_tracker.mapper.WorkoutExerciseMapper;
import com.vadim.tkach.gym_tracker.mapper.WorkoutMapper;
import com.vadim.tkach.gym_tracker.repository.*;
import com.vadim.tkach.gym_tracker.repository.entity.*;
import com.vadim.tkach.gym_tracker.service.model.Exercise;
import com.vadim.tkach.gym_tracker.service.model.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutMapper workoutMapper;
    private final WorkoutExerciseMapper workoutExerciseMapper;

    @Override
    public void createWorkout(Workout workout) {

        UserEntity user = userRepository.findById(workout.getUserId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + workout.getUserId()));

        WorkoutEntity workoutEntity = workoutMapper.toWorkoutEntity(workout, user);

        List<WorkoutExerciseEntity> exercises = workout.getExercises()
                .stream()
                .map(we -> mapWorkoutExercise(we, workoutEntity))
                .toList();

        workoutEntity.setExercises(exercises);

        workoutRepository.save(workoutEntity);
    }

    @Override
    public void updateWorkout(Workout workout) {

        WorkoutEntity workoutEntity = workoutRepository.findById(workout.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Workout not found with id: " + workout.getId()));

        workoutEntity.setName(workout.getName());
        workoutEntity.setDate(workout.getDate());
        workoutEntity.setNote(workout.getNote());
        workoutEntity.setWorkoutType(workout.getType());

        workoutEntity.getExercises().clear();

        List<WorkoutExerciseEntity> updatedExercises = workout.getExercises()
                .stream()
                .map(we -> mapWorkoutExercise(we, workoutEntity))
                .toList();

        workoutEntity.getExercises().addAll(updatedExercises);
    }

    private WorkoutExerciseEntity mapWorkoutExercise(
            com.vadim.tkach.gym_tracker.service.model.WorkoutExercise we,
            WorkoutEntity workoutEntity
    ) {
        WorkoutExerciseEntity entity = workoutExerciseMapper.toWorkoutExerciseEntity(we);

        ExerciseEntity exercise = exerciseRepository.findById(we.getExerciseId())
                .orElseThrow(() -> new RuntimeException(
                        "Exercise not found with id: " + we.getExerciseId()));

        entity.setWorkout(workoutEntity);
        entity.setExercise(exercise);

        return entity;
    }

    @Override
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll()
                .stream()
                .map(workoutMapper::toWorkout)
                .toList();
    }

    @Override
    public Workout getWorkoutById(UUID id) {
        return workoutRepository.findById(id)
                .map(workoutMapper::toWorkout)
                .orElseThrow(() -> new RuntimeException("Workout not found: " + id));
    }

    @Override
    public void deleteWorkout(UUID id) {
        workoutRepository.deleteById(id);
    }

    @Override
    public List<Workout> getWorkoutsByUserId(UUID userId) {
        return workoutRepository.findByUserId(userId)
                .stream()
                .map(workoutMapper::toWorkout)
                .toList();
    }
}

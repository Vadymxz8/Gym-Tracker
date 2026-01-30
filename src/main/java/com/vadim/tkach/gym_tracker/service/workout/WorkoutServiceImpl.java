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
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutExerciseMapper workoutExerciseMapper;
    private final WorkoutMapper workoutMapper;

    @Override
    public void createWorkout(Workout workout) {
        UserEntity userEntity = userRepository.findById(workout.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + workout.getUserId()));

        WorkoutEntity workoutEntity = workoutMapper.toWorkoutEntity(workout, userEntity);

        List<WorkoutExerciseEntity> workoutExercises = workout.getExercises().stream()
                .map(we -> {
                    WorkoutExerciseEntity entity = workoutExerciseMapper.toWorkoutExerciseEntity(we);

                    ExerciseEntity exerciseEntity = null;
                    if (we.getExerciseId() != null) {
                        exerciseEntity = exerciseRepository.findById(we.getExerciseId())
                                .orElseThrow(() -> new RuntimeException("Exercise not found with id: " + we.getExerciseId()));
                    } else if (we.getExercise() != null) {
                        exerciseEntity = workoutExerciseMapper.toExerciseEntity(we.getExercise());
                        exerciseRepository.save(exerciseEntity);
                    } else {
                        throw new RuntimeException("Exercise information is missing.");
                    }

                    entity.setWorkout(workoutEntity);
                    entity.setExercise(exerciseEntity);
                    return entity;
                }).collect(Collectors.toList());

        workoutEntity.setWorkoutExercises(workoutExercises);

        workoutRepository.save(workoutEntity);
    }

    @Override
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll()
                .stream()
                .map(workoutMapper::toWorkout)
                .collect(Collectors.toList());
    }

    @Override
    public Workout getWorkoutById(UUID id) {
        WorkoutEntity entity = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found with id: " + id));
        return workoutMapper.toWorkout(entity);
    }

    @Override
    @Transactional
    public void updateWorkout(Workout workout) {
        WorkoutEntity workoutEntity = workoutRepository.findById(workout.getId())
                .orElseThrow(() -> new RuntimeException("Workout not found with id: " + workout.getId()));

        workoutEntity.setName(workout.getName());
        workoutEntity.setDate(workout.getDate());
        workoutEntity.setNote(workout.getNote());
        workoutEntity.setWorkoutType(workout.getType());

        workoutEntity.getWorkoutExercises().clear();

        List<WorkoutExerciseEntity> updatedExercises = workout.getExercises().stream()
                .map(we -> {
                    WorkoutExerciseEntity entity = workoutExerciseMapper.toWorkoutExerciseEntity(we);

                    ExerciseEntity exerciseEntity = null;
                    if (we.getExerciseId() != null) {
                        exerciseEntity = exerciseRepository.findById(we.getExerciseId())
                                .orElseThrow(() -> new RuntimeException("Exercise not found with id: " + we.getExerciseId()));
                    } else if (we.getExercise() != null) {
                        exerciseEntity = workoutExerciseMapper.toExerciseEntity(we.getExercise());
                        exerciseRepository.save(exerciseEntity);
                    } else {
                        throw new RuntimeException("Exercise information is missing.");
                    }

                    entity.setWorkout(workoutEntity);
                    entity.setExercise(exerciseEntity);
                    return entity;
                })
                .collect(Collectors.toList());

        workoutEntity.setWorkoutExercises(updatedExercises);

        workoutRepository.save(workoutEntity);
    }

    @Override
    public void deleteWorkout(UUID id) {
        if (!workoutRepository.existsById(id)) {
            throw new RuntimeException("Workout not found with id: " + id);
        }
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

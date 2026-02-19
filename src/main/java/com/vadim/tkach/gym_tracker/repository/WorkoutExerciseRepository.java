package com.vadim.tkach.gym_tracker.repository;

import com.vadim.tkach.gym_tracker.repository.entity.WorkoutExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExerciseEntity, UUID> {

    List<WorkoutExerciseEntity> findByWorkoutId(UUID workoutId);

    List<WorkoutExerciseEntity> findByWorkoutUserIdAndWorkoutDateBetween(
            UUID userId, LocalDate from, LocalDate to
    );
}

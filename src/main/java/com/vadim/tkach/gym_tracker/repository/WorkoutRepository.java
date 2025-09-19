package com.vadim.tkach.gym_tracker.repository;

import com.vadim.tkach.gym_tracker.repository.entity.WorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface WorkoutRepository extends JpaRepository<WorkoutEntity, UUID> {

    List<WorkoutEntity> findByUserId(UUID userId);


    List<WorkoutEntity> findByUserIdAndDateBetweenOrderByDateAsc(UUID userId, LocalDate from, LocalDate to);

    long countByUserIdAndDateBetween(UUID userId, LocalDate from, LocalDate to);
}

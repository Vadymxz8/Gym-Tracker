package com.vadim.tkach.gym_tracker.repository;

import com.vadim.tkach.gym_tracker.repository.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntity, UUID> { }


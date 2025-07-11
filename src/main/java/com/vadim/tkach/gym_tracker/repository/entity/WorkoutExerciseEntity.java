package com.vadim.tkach.gym_tracker.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "workout_exercise")
public class WorkoutExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private Integer reps;
    @Column
    private Double weight;
    @Column
    private Integer sets;
    @Column
    private String note;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private ExerciseEntity exercise;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private WorkoutEntity workout;
}


package com.vadim.tkach.gym_tracker.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "exercises")
public class ExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String name;
    @Column
    private String type;

    @Column(name = "muscle_group")
    private String muscleGroup;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutExerciseEntity> workoutExercises;
}


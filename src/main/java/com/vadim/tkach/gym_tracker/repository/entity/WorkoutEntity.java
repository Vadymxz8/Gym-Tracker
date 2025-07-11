package com.vadim.tkach.gym_tracker.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "workouts")
public class WorkoutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private LocalDate date;
    @Column
    private String name;

    @Column(name = "workout_type")
    private String workoutType;
    @Column
    private String note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutExerciseEntity> workoutExercises;
}

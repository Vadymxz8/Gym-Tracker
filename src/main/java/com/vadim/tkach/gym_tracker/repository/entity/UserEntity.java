package com.vadim.tkach.gym_tracker.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutEntity> workouts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReminderEntity> reminders;
}

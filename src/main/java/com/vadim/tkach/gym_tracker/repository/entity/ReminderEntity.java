package com.vadim.tkach.gym_tracker.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "reminders")
public class ReminderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private OffsetDateTime time;
    @Column
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}


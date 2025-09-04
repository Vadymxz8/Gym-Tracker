package com.vadim.tkach.gym_tracker.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {
    private UUID id;
    private OffsetDateTime time;
    private String message;
    private UUID userId;
}


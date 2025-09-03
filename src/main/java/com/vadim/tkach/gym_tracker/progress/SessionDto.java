package com.vadim.tkach.gym_tracker.progress;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SessionDto {
    private UUID id;
    private LocalDate date;
    private String name;
    private String note;
}


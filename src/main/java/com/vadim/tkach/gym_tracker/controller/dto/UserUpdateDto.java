package com.vadim.tkach.gym_tracker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class UserUpdateDto {
    private UUID id;
    private String name;
    private String email;
    private String password;
}

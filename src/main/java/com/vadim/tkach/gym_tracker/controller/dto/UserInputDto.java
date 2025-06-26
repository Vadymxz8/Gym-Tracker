package com.vadim.tkach.gym_tracker.controller.dto;

import lombok.Data;

@Data
public class UserInputDto {
    private final String name;
    private final String email;
    private final String password;
}

package com.vadim.tkach.gym_tracker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class AuthTokenDto {
    private String token;
}


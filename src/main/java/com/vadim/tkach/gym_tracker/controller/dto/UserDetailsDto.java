package com.vadim.tkach.gym_tracker.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@AllArgsConstructor
@Data
@Builder
public class UserDetailsDto {
    private  UUID id;
    private  String name;
    private  String email;
}
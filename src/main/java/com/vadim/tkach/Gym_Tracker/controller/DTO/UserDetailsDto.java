package com.vadim.tkach.Gym_Tracker.controller.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDetailsDto {
private final UUID id;
private final String name;
private final String email;
}

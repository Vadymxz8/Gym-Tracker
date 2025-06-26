package com.vadim.tkach.gym_tracker.mapper;

import com.vadim.tkach.gym_tracker.controller.dto.UserDetailsDto;
import com.vadim.tkach.gym_tracker.controller.dto.UserInputDto;
import com.vadim.tkach.gym_tracker.controller.dto.UserUpdateDto;
import com.vadim.tkach.gym_tracker.service.domain.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {

    public UserDetailsDto toUserDetailsDto(User user) {
        return UserDetailsDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public User toUser(UserInputDto userDetailsDto) {
        return User.builder()
                .id(UUID.randomUUID())
                .name(userDetailsDto.getName())
                .email(userDetailsDto.getEmail())
                .password(userDetailsDto.getPassword())
                .build();
    }
    public User toUser(UserUpdateDto userDetailsDto) {
        return User.builder()
                .name(userDetailsDto.getName())
                .email(userDetailsDto.getEmail())
                .build();
    }
}

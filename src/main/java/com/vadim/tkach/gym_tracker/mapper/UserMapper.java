package com.vadim.tkach.gym_tracker.mapper;

import com.vadim.tkach.gym_tracker.controller.dto.UserDetailsDto;
import com.vadim.tkach.gym_tracker.controller.dto.UserInputDto;
import com.vadim.tkach.gym_tracker.controller.dto.UserUpdateDto;
import com.vadim.tkach.gym_tracker.repository.entity.UserEntity;
import com.vadim.tkach.gym_tracker.service.model.User;
import com.vadim.tkach.gym_tracker.service.model.UserStatus;
import org.springframework.stereotype.Component;

import static com.fasterxml.jackson.databind.util.ClassUtil.name;

@Component
public class UserMapper {

    public UserDetailsDto toUserDetailsDto(User user) {
        return UserDetailsDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public User toUser(UserInputDto userInputDto) {
        return User.builder()
                .name(userInputDto.getName())
                .email(userInputDto.getEmail())
                .build();
    }
    public User toUser(UserUpdateDto userDetailsDto) {
        return User.builder()
                .id(userDetailsDto.getId())
                .name(userDetailsDto.getName())
                .email(userDetailsDto.getEmail())
                .build();
    }

    public User toUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .token(userEntity.getToken())
                .status(userEntity.getStatus() != null ? UserStatus.valueOf(userEntity.getStatus()) : null)
                .build();
    }

    public UserEntity toUserEntity(User user) {
        return UserEntity.builder()
                .id(user.getId() != null ? user.getId() : null)
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .token(user.getToken())
                .status(user.getStatus() != null ? user.getStatus().name() : null)
                .build();
    }

}




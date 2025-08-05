package com.vadim.tkach.gym_tracker.controller;

import com.vadim.tkach.gym_tracker.controller.dto.UserDetailsDto;
import com.vadim.tkach.gym_tracker.controller.dto.UserInputDto;
import com.vadim.tkach.gym_tracker.controller.dto.UserUpdateDto;
import com.vadim.tkach.gym_tracker.mapper.UserMapper;
import com.vadim.tkach.gym_tracker.service.user.UserService;
import com.vadim.tkach.gym_tracker.service.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping
    public ResponseEntity<List<UserDetailsDto>> getUsers() {
        List<UserDetailsDto> userDetailsDtoList =
                userService.getAllUsers().stream()
                        .map(userMapper::toUserDetailsDto)
                        .toList();
        return new ResponseEntity<>(userDetailsDtoList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserInputDto userInputDto) {

        userService.createUser(userMapper.toUser(userInputDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable UUID id) {

        User user = userService.getUser(id);
        UserDetailsDto userDetailsDto = userMapper.toUserDetailsDto(user);
        return new ResponseEntity<>(userDetailsDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        log.info("Deleted user with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable UUID id,
            @RequestBody UserUpdateDto dto) {

        User user = userMapper.toUser(dto);
        user.setId(id);
        userService.updateUser(user);

        return ResponseEntity.ok().build();
    }
}


package com.vadim.tkach.Gym_Tracker.controller;

import com.vadim.tkach.Gym_Tracker.controller.DTO.UserDetailsDto;
import com.vadim.tkach.Gym_Tracker.controller.DTO.UserInputDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {

    private final Map<UUID, UserDetailsDto> userDB = new HashMap<>();

    public UsersController() {
        UUID vasyaId = UUID.randomUUID();
        UserDetailsDto vasya = new UserDetailsDto(vasyaId, "Vasya", "vasya.pupkin@example.com");
        userDB.put(vasyaId, vasya);
        log.info("Preloaded user: {}", vasya);
    }

    @GetMapping
    public ResponseEntity<List<UserDetailsDto>> getAllUsers() {
        List<UserDetailsDto> usersDto = new ArrayList<>(userDB.values());
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserInputDto userInputDto) {
        UUID userId = UUID.randomUUID();
        UserDetailsDto newUser = new UserDetailsDto(userId, userInputDto.getName(), userInputDto.getEmail());

        userDB.put(userId, newUser);
        log.info("Creating new user");
        log.info("User created: {}", newUser);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable UUID id) {
        UserDetailsDto user = userDB.get(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userDB.remove(id);
        log.info("Deleted user with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

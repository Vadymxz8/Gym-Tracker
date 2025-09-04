package com.vadim.tkach.gym_tracker.controller;

import com.vadim.tkach.gym_tracker.controller.dto.AuthCredentialsDto;
import com.vadim.tkach.gym_tracker.controller.dto.AuthTokenDto;
import com.vadim.tkach.gym_tracker.controller.dto.UserDetailsDto;
import com.vadim.tkach.gym_tracker.controller.dto.UserRegistrationCompleteDto;
import com.vadim.tkach.gym_tracker.service.login.LoginService;
import com.vadim.tkach.gym_tracker.service.user.UserService;
import com.vadim.tkach.gym_tracker.service.model.User;
import com.vadim.tkach.gym_tracker.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final LoginService loginService;

    @GetMapping("/registration-confirm")
    public ResponseEntity<UserDetailsDto> confirmRegistration(
            @RequestParam(name = "token") String token) {

        User user = userService.getByToken(token);
        log.info("Confirming registration with token: {}", token);

        return new ResponseEntity<>(userMapper.toUserDetailsDto(user),
                HttpStatus.OK);
    }

    @PostMapping("/registration-complete")
    public ResponseEntity<Void> completeRegistration(
            @RequestBody UserRegistrationCompleteDto userRegistrationComplete) {

        userService.completeRegistration(
                userRegistrationComplete.getToken(),
                userRegistrationComplete.getPassword());

        return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public ResponseEntity<AuthTokenDto> login(@RequestBody AuthCredentialsDto authCredentialsDto) {
        log.info("Login request for email='{}'", authCredentialsDto.getEmail());
        String token = loginService.login(authCredentialsDto.getEmail(), authCredentialsDto.getPassword());
        log.info("Login success for email='{}', tokenGenerated={}", authCredentialsDto.getEmail(), token != null);
        return new ResponseEntity<>(new AuthTokenDto(token), HttpStatus.OK);
    }

}

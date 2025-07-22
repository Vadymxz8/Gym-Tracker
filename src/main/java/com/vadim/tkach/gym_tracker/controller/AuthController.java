//package com.vadim.tkach.gym_tracker.controller;
//
//import com.vadim.tkach.gym_tracker.controller.dto.AuthCredentialsDto;
//import com.vadim.tkach.gym_tracker.controller.dto.AuthTokenDto;
//import com.vadim.tkach.gym_tracker.controller.dto.PasswordChangeDto;
//import com.vadim.tkach.gym_tracker.controller.dto.PasswordCompleteDto;
//import com.vadim.tkach.gym_tracker.controller.dto.UserDetailsDto;
//import com.vadim.tkach.gym_tracker.controller.dto.UserRegistrationCompleteDto;
//import com.vadim.tkach.gym_tracker.service.AuthService;
//import com.vadim.tkach.gym_tracker.service.model.User;
//import com.vadim.tkach.gym_tracker.mapper.UserMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final AuthService authService;
//    private final UserMapper userMapper;
//
//    @GetMapping("/registration-confirm")
//    public ResponseEntity<UserDetailsDto> confirmRegistration(@RequestParam(name = "token") String token) {
//        User user = authService.getByToken(token);
//        log.info("Confirming registration with token: {}", token);
//        return new ResponseEntity<>(userMapper.toUserDetailsDto(user), HttpStatus.OK);
//    }
//
//    @PostMapping("/registration-complete")
//    public ResponseEntity<Void> completeRegistration(@RequestBody UserRegistrationCompleteDto registrationCompleteDto) {
//        authService.completeRegistration(registrationCompleteDto.getToken(), registrationCompleteDto.getPassword());
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthTokenDto> login(@RequestBody AuthCredentialsDto authCredentialsDto) {
//        String token = authService.login(authCredentialsDto.getEmail(), authCredentialsDto.getPassword());
//        return new ResponseEntity<>(new AuthTokenDto(token), HttpStatus.OK);
//    }
//
//    @PostMapping("/password-change")
//    public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
//        authService.changePassword(passwordChangeDto.getEmail(), passwordChangeDto.getNewPassword());
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/password-complete")
//    public ResponseEntity<Void> completePasswordReset(@RequestBody PasswordCompleteDto passwordCompleteDto) {
//        authService.completePasswordReset(passwordCompleteDto.getToken(), passwordCompleteDto.getNewPassword());
//        return ResponseEntity.ok().build();
//    }
//}

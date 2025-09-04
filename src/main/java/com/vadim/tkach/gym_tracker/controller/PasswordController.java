package com.vadim.tkach.gym_tracker.controller;

import com.vadim.tkach.gym_tracker.controller.dto.*;
import com.vadim.tkach.gym_tracker.mapper.UserMapper;
import com.vadim.tkach.gym_tracker.service.model.User;
import com.vadim.tkach.gym_tracker.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users/passwords")
@RequiredArgsConstructor
public class PasswordController {

    private final UserService userService;
    private final UserMapper userMapper;

    // PUT /users/passwords — зміна пароля авторизованого користувача
    @PutMapping
    public ResponseEntity<Void> changeOwnPassword(@RequestBody PasswordChangeDto dto) {
        log.info("Change password request for authenticated user");
        userService.changeOwnPassword(dto.getOldPassword(), dto.getNewPassword());
        return ResponseEntity.ok().build();
    }

    // PUT /users/passwords/reset — запит на скидання пароля
    @PutMapping("/reset")
    public ResponseEntity<Void> requestPasswordReset(@RequestBody PasswordRequestDto dto) {
        log.info("Password reset requested for email={}", dto.getEmail());
        userService.requestPasswordReset(dto.getEmail());
        return ResponseEntity.ok().build();
    }

    // GET /users/passwords/reset-verify?token=... — перевірка токена
    @GetMapping("/reset-verify")
    public ResponseEntity<UserDetailsDto> verifyResetToken(@RequestParam("token") String token) {
        log.info("Verify reset token {}", token);
        User user = userService.verifyPasswordResetToken(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserDetailsDto(user));
    }

    // POST /users/passwords/reset-complete — завершення скидання
    @PostMapping("/reset-complete")
    public ResponseEntity<Void> completePasswordReset(@RequestBody PasswordCompleteDto dto) {
        log.info("Complete password reset for token={}", dto.getToken());
        userService.completePasswordReset(dto.getToken(), dto.getPassword());
        return ResponseEntity.ok().build();
    }
}


package com.vadim.tkach.gym_tracker.service.login;

import com.vadim.tkach.gym_tracker.exception.PasswordIncorrectException;
import com.vadim.tkach.gym_tracker.exception.UserNotFoundException;
import com.vadim.tkach.gym_tracker.mapper.UserMapper;
import com.vadim.tkach.gym_tracker.repository.UserRepository;
import com.vadim.tkach.gym_tracker.service.model.User;
import com.vadim.tkach.gym_tracker.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

@Override
public String login(String email, String password) {
    log.debug("LoginService.login called for email='{}'", email);

    var optEntity = userRepository.findByEmailIgnoreCase(email);
    if (optEntity.isEmpty()) {
        log.warn("User not found for email={}", email);
        throw new UserNotFoundException("User with " + email + " not found");
    }

    var userEntity = optEntity.get();
    log.debug("Found UserEntity: id={}, email={}, passwordHashPresent={}",
            userEntity.getId(), userEntity.getEmail(), userEntity.getPassword() != null);

    User user = userMapper.toUser(userEntity);
    log.debug("Mapped User: id={}, passwordPresent={}", user.getId(), user.getPassword() != null);

    boolean matches = passwordEncoder.matches(password, user.getPassword());
    log.debug("Password match for email='{}' => {}", email, matches);

    if (!matches) {
        log.warn("Password mismatch for email={}", email);
        throw new PasswordIncorrectException("Password was incorrect for user with email " + email);
    }

    String token = tokenService.createToken(user);
    log.info("Token generated for userId={}", user.getId());
    return token;
}

}

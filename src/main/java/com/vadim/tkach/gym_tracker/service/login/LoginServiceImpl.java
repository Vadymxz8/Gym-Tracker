package com.vadim.tkach.gym_tracker.service.login;

import com.vadim.tkach.gym_tracker.exception.PasswordIncorrectException;
import com.vadim.tkach.gym_tracker.exception.UserNotFoundException;
import com.vadim.tkach.gym_tracker.mapper.UserMapper;
import com.vadim.tkach.gym_tracker.repository.UserRepository;
import com.vadim.tkach.gym_tracker.service.model.User;
import com.vadim.tkach.gym_tracker.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public String login(String email, String password) {

        User user =
                userRepository
                        .findByEmailIgnoreCase(email)
                        .map(userMapper::toUser)
                        .orElseThrow(() -> new UserNotFoundException("User with " + email + " not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordIncorrectException("Password was incorrect for user with email " + email);
        }

        return tokenService.createToken(user);
    }
}

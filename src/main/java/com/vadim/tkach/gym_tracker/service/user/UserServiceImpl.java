package com.vadim.tkach.gym_tracker.service.user;

import com.vadim.tkach.gym_tracker.exception.UserNotFoundException;
import com.vadim.tkach.gym_tracker.mapper.UserMapper;
import com.vadim.tkach.gym_tracker.repository.UserRepository;
import com.vadim.tkach.gym_tracker.repository.entity.UserEntity;
import com.vadim.tkach.gym_tracker.service.email.EmailService;
import com.vadim.tkach.gym_tracker.service.model.User;
import com.vadim.tkach.gym_tracker.service.model.UserStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final HttpServletRequest request;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setStatus(UserStatus.PENDING);
        UUID token = UUID.randomUUID();
        user.setToken(token);

        userRepository.save(userMapper.toUserEntity(user));

        String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        emailService.sendEmail(
                user.getEmail(),
                "Confirm registration",
                "Please confirm your registration by clicking the link below:\n"
                        + baseUrl
                        + "/users/registration-confirm?token="
                        + token);

        log.info("User created with email: {}", user.getEmail());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUser)
                .toList();
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toUser)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public User getByToken(String token) {
        return userRepository.findByToken(UUID.fromString(token))
                .map(userMapper::toUser)
                .orElseThrow(() -> new UserNotFoundException("User with token " + token + " not found"));
    }

    @Override
    public void updateUser(User user) {
        log.info("Updating user with id: {}", user.getId());
        Optional<UserEntity> optionalUserEntity = userRepository.findById(user.getId());

        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            userEntity.setName(user.getName());
            userEntity.setEmail(user.getEmail());

            userRepository.save(userEntity);
            log.info("User {} updated successfully", user.getId());
        } else {
            throw new UserNotFoundException("User with id " + user.getId() + " not found");
        }
    }

    @Override
    public void deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("Deleted user with id: {}", id);
        } else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

    @Override
    public void completeRegistration(String userId, String password) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(UUID.fromString(userId));

        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();

            String encodedPassword = passwordEncoder.encode(password);
            userEntity.setPassword(encodedPassword);
            userEntity.setStatus(UserStatus.ACTIVE.name());
            userEntity.setToken(null);

            userRepository.save(userEntity);
            log.info("User {} completed registration", userId);
        } else {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
    }
}
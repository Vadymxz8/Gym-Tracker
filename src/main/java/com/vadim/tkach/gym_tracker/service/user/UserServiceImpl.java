package com.vadim.tkach.gym_tracker.service.user;

import com.vadim.tkach.gym_tracker.exception.PasswordIncorrectException;
import com.vadim.tkach.gym_tracker.exception.UserNotFoundException;
import com.vadim.tkach.gym_tracker.mapper.UserMapper;
import com.vadim.tkach.gym_tracker.repository.UserRepository;
import com.vadim.tkach.gym_tracker.repository.entity.UserEntity;
import com.vadim.tkach.gym_tracker.service.email.EmailService;
import com.vadim.tkach.gym_tracker.service.model.User;
import com.vadim.tkach.gym_tracker.service.model.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${frontend.base-url}")
    private String frontendBaseUrl;

    @Value("${password.reset.ttl-hours:24}")
    private long resetTtlHours;

    @Override
    public void createUser(User user) {
        user.setStatus(UserStatus.PENDING);
        log.info("Creating new user");

        user.setStatus(UserStatus.PENDING);

        UUID token = UUID.randomUUID();
        user.setToken(token);

        userRepository.save(userMapper.toUserEntity(user));

//        emailService.sendEmail(
//                user.getEmail(),
//                "Confirm registration",
//                "Please confirm your registration by clicking the link below:\n"
//                        + frontendBaseUrl
//                        + "/users/registration-confirm?token="
//                        + token
//        );
        try {
            emailService.sendEmail(...);
        } catch (Exception e) {
            log.warn("Email sending failed, but user created", e);
        }


        log.info("User created: {}", user);
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

        UserEntity entity = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User with id " + user.getId() + " not found"));

        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setStatus(user.getStatus().name());

        userRepository.save(entity);
    }

    @Override
    public void deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

    @Override
    public void completeRegistration(String token, String password) {
        UserEntity entity = userRepository.findByToken(UUID.fromString(token))
                .orElseThrow(() -> new UserNotFoundException("User with token " + token + " not found"));

        entity.setPassword(passwordEncoder.encode(password));
        entity.setStatus(UserStatus.ACTIVE.name());
        entity.setToken(null);

        userRepository.save(entity);

        log.info("User {} completed registration", entity.getId());
    }

    @Override
    public User getAuthenticatedUser() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUser(UUID.fromString(userId));
    }






    @Override
    public void changeOwnPassword(String oldPassword, String newPassword) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity entity = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        if (!passwordEncoder.matches(oldPassword, entity.getPassword())) {
            throw new PasswordIncorrectException("Old password is incorrect");
        }

        entity.setPassword(passwordEncoder.encode(newPassword));
        entity.setUpdatedAt(Instant.now());
        userRepository.save(entity);

        log.info("User {} changed password", entity.getId());
    }

    @Override
    public void requestPasswordReset(String email) {
        userRepository.findByEmailIgnoreCase(email).ifPresent(entity -> {
            UUID resetToken = UUID.randomUUID();
            Instant expiresAt = Instant.now().plus(resetTtlHours, ChronoUnit.HOURS);

            entity.setResetToken(resetToken);
            entity.setResetTokenExpiresAt(expiresAt);
            userRepository.save(entity);

            String link = frontendBaseUrl + "/users/passwords/reset-verify?token=" + resetToken;
            emailService.sendEmail(
                    entity.getEmail(),
                    "Password reset",
                    "To reset your password please follow the link below (valid for " + resetTtlHours + "h):\n" + link
            );

            log.info("Password reset token generated for userId={}, expiresAt={}", entity.getId(), expiresAt);
        });

        // Навмисно не розкриваємо існування email — завжди 200 OK
    }

    @Override
    public User verifyPasswordResetToken(String token) {
        UserEntity entity = userRepository.findByResetToken(parseUUID(token))
                .orElseThrow(() -> new UserNotFoundException("Reset token not found"));

        if (entity.getResetTokenExpiresAt() == null || entity.getResetTokenExpiresAt().isBefore(Instant.now())) {
            throw new IllegalStateException("Reset token expired");
        }

        return userMapper.toUser(entity);
    }

    @Override
    public void completePasswordReset(String token, String newPassword) {
        UserEntity entity = userRepository.findByResetToken(parseUUID(token))
                .orElseThrow(() -> new UserNotFoundException("Reset token not found"));

        if (entity.getResetTokenExpiresAt() == null || entity.getResetTokenExpiresAt().isBefore(Instant.now())) {
            throw new IllegalStateException("Reset token expired");
        }

        entity.setPassword(passwordEncoder.encode(newPassword));
        entity.setResetToken(null);
        entity.setResetTokenExpiresAt(null);
        entity.setUpdatedAt(Instant.now());

        userRepository.save(entity);
        log.info("Password reset completed for userId={}", entity.getId());
    }

    private UUID parseUUID(String s) {
        try {
            return UUID.fromString(s);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid token format");
        }
    }

}

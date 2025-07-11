package com.vadim.tkach.gym_tracker.service;

import com.vadim.tkach.gym_tracker.exception.UserNotFoundException;
import com.vadim.tkach.gym_tracker.mapper.UserMapper;
import com.vadim.tkach.gym_tracker.service.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.vadim.tkach.gym_tracker.repository.UserRepository;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
   private Map<UUID, User> userMap = new HashMap<>();

    @Override
    public void createUser(User user) {


        userRepository.save(userMapper.toUserEntity(user));
//        log.info("Creating new user");
//        user.setId(UUID.randomUUID());
//        userMap.put(user.getId(), user);
//
//        log.info("User created: {}", user);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());

    }

    @Override
    public User getUser(UUID id) {
        if (userMap.containsKey(id)) {
            return userMap.get(id);
        }
        throw new UserNotFoundException("Admin with id " + id + " not found");
    }

    @Override
    public void updateUser(User user) {
        log.info("Updating user with id: {}", user.getId());
        if (userMap.containsKey(user.getId())) {
            userMap.put(user.getId(), user);
            log.info("Admin updated: {}", user);
        } else {
            throw new UserNotFoundException("User with id " + user.getId() + " not found");
        }
    }

    @Override
    public void deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
    userRepository.deleteById(id);
    }else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
}
}

package com.vadim.tkach.gym_tracker.service;

import com.vadim.tkach.gym_tracker.exception.UserNotFoundException;
import com.vadim.tkach.gym_tracker.mapper.UserMapper;
import com.vadim.tkach.gym_tracker.repository.entity.UserEntity;
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
Optional<UserEntity> optionalUserEntity =
        userRepository.findById(id);
if(optionalUserEntity.isPresent()){
    return userMapper.toUser(optionalUserEntity.get());
}
        throw new UserNotFoundException("Admin with id " + id + " not found");
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

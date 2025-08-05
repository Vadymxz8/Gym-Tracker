package com.vadim.tkach.gym_tracker.repository;

import com.vadim.tkach.gym_tracker.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity>findByToken(UUID token);

    Optional<UserEntity> findByEmailIgnoreCase(String email);
    boolean existsByEmail(String email);
}

package org.wigo.myday.repository;

import org.wigo.myday.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByVerificationCode(String verificationCode);
    Optional<UserEntity> findByUsername(String username);
}
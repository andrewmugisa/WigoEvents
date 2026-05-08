package org.wigo.myday.repository;

import org.wigo.myday.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Integer> {
    Optional<UsersEntity> findById(int id);
    Optional<UsersEntity> findByEmail(String email);
    Optional<UsersEntity> findByEmailAndPassword(String email, String password);
}
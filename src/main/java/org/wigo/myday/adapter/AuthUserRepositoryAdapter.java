package org.wigo.myday.adapter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.wigo.auth.repository.AuthUserRepository;
import org.wigo.myday.model.UserEntity;
import org.wigo.myday.repository.UserRepository;
import java.util.Optional;

@Repository
public class AuthUserRepositoryAdapter implements AuthUserRepository {

    private final UserRepository userRepository;

    public AuthUserRepositoryAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserDetails> findByEmail(String email) {
        return userRepository.findByEmail(email).map(u -> (UserDetails) u);
    }

    @Override
    public Optional<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username).map(u -> (UserDetails) u);
    }

    @Override
    public Optional<UserDetails> findByVerificationCode(String code) {
        return userRepository.findByVerificationCode(code).map(u -> (UserDetails) u);
    }

    @Override
    public UserDetails save(UserDetails user) {
        return userRepository.save((UserEntity) user);
    }
}

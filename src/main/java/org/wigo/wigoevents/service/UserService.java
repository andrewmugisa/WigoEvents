package org.wigo.wigoevents.service;

import org.springframework.stereotype.Service;
import org.wigo.wigoevents.model.UserEntity;
import org.wigo.wigoevents.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity updateUsername(Integer userId, String newUsername) {
        if (newUsername == null || newUsername.isBlank())
            throw new IllegalArgumentException("Username cannot be blank");
        String cleaned = newUsername.toLowerCase().replaceAll("\\s+", "").replaceAll("[^a-z0-9_]", "");
        if (cleaned.length() < 3)
            throw new IllegalArgumentException("Username must be at least 3 characters");
        if (userRepository.findByUsername(cleaned).isPresent())
            throw new IllegalArgumentException("Username is already taken");
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(cleaned);
        return userRepository.save(user);
    }

    public List<UserEntity> allUsers() {
        List<UserEntity> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}

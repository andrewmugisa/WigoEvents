package org.wigo.myday.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wigo.myday.model.UserEntity;
import org.wigo.myday.repository.UserRepository;
import org.wigo.myday.service.Jwt.EmailService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
    }

    public UserEntity updateUsername(Integer userId, String newUsername) {
        if (newUsername == null || newUsername.isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }

        String cleaned = newUsername.toLowerCase()
                .replaceAll("\\s+", "")
                .replaceAll("[^a-z0-9_]", "");

        if (cleaned.length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters");
        }

        if (userRepository.findByUsername(cleaned).isPresent()) {
            throw new IllegalArgumentException("Username is already taken");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(cleaned);
        return userRepository.save(user);
    }


    //read all users
    public List<UserEntity> allUsers() {
        List<UserEntity> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}

package org.wigo.myday.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.wigo.myday.dto.UpdateUsernameDto;
import org.wigo.myday.model.UserEntity;
import org.wigo.myday.response.UserResponse;
import org.wigo.myday.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> authenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        return ResponseEntity.ok(new UserResponse(user));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.allUsers()
                .stream()
                .map(UserResponse::new)
                .toList();
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/me/username")
    public ResponseEntity<UserResponse> updateUsername(@Valid @RequestBody UpdateUsernameDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) auth.getPrincipal();

        UserEntity updated = userService.updateUsername(currentUser.getUserId(), dto.getUsername());
        return ResponseEntity.ok(new UserResponse(updated));
    }
}
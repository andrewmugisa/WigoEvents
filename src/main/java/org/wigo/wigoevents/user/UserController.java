package org.wigo.wigoevents.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.wigo.auth.response.ApiResponse;

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
        return ResponseEntity.ok(userService.allUsers().stream().map(UserResponse::new).toList());
    }

    @PatchMapping("/me/username")
    public ResponseEntity<UserResponse> updateUsername(@Valid @RequestBody UpdateUsernameDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) auth.getPrincipal();
        return ResponseEntity.ok(new UserResponse(userService.updateUsername(currentUser.getUserId(), dto.getUsername())));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse> deleteAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) auth.getPrincipal();
        userService.deleteUser(currentUser.getUserId());
        return ResponseEntity.ok(new ApiResponse("Account deleted successfully."));
    }
}

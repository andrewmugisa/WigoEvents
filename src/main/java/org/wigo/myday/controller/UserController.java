package org.wigo.myday.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.wigo.myday.model.UserEntity;
import org.wigo.myday.service.UserService;

import java.util.List;


@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserEntity> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

}

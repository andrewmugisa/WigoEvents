package org.wigo.myday.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.wigo.myday.entity.UsersEntity;
import org.wigo.myday.service.UserService;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
//@RequestMapping("/api/")
public class UserController {
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UsersEntity> getUsers(){
        return userService.readUserDetails();
    }

    @GetMapping("/api/home/{id}")
    public Optional<UsersEntity> getUser(@PathVariable int id){
        return userService.readUserDetails(id);
    }

    @PostMapping("/api/login")
    public Optional<UsersEntity> login(@RequestBody UsersEntity user){
        return userService.login(user.getEmail(), user.getPassword());
    }


}

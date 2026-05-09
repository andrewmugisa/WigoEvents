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


    //read all users
    public List<UserEntity> allUsers() {
        List<UserEntity> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }






    //create user
//
//    //read user data
//    public Optional<UserEntity> readUserDetails(int id){
//        return userRepository.findById(id);
//    }
//
//    public Optional<UserEntity> login(String email, String password) {
//        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
//            return Optional.empty();
//        }
//
//        return userRepository.findByEmail(email);
//    }
//
//    //update user
//    //delete user

}

package org.wigo.myday.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wigo.myday.entity.UsersEntity;
import org.wigo.myday.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //read all users
    public List<UsersEntity> readUserDetails(){
        return userRepository.findAll();
    }
    //create user

    //read user data
    public Optional<UsersEntity> readUserDetails(int id){
        return userRepository.findById(id);
    }

    public Optional<UsersEntity> login(String email, String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return Optional.empty();
        }

        return userRepository.findByEmailAndPassword(email, password);
    }

    //update user
    //delete user

}

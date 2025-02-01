package com.example.demo.services;

import com.example.demo.models.UserModel;
import com.example.demo.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean createUser(UserModel userModel) {
        this.userRepository.save(userModel);
        return true;
    }


    public List<UserModel> getAllUsers() {
        return this.userRepository.findByIsDeleted(0);
    }

    public UserModel getUser(int id) {
        return this.userRepository.findByIdAndIsDeleted(id,0);
    }
    //somehow this works, could improve and make the fields optional
    public boolean updateUser(UserModel user, int id) {
        // Find the user by ID and update its fields if found
        return userRepository.findById(id).map(userModel -> {
            userModel.setUsername(user.getUsername());
            userModel.setPassword(user.getPassword());
            userRepository.save(userModel); // Save the updated userModel
            return true; // Indicate successful update
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }


    public boolean removeUser(int id) {
        UserModel user = getUser(id);
        if(user!= null){
            user.setIsDeleted(1);    //needs getter and setter first
            this.userRepository.save(user);
            return true;
        }

        return false;
    }

    public UserModel getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

//    public boolean getUserByUsername(String username) {
//        if (this.userRepository.findByUsername(username)){
//            return true;
//        }
//        return false;
//    }





}

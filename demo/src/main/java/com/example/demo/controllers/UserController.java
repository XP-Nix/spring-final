package com.example.demo.controllers;


import com.example.demo.http.AppResponse;
import com.example.demo.models.UserModel;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;


    //CREATE
    @PostMapping("/users")
    public ResponseEntity<?> createNewUser(@RequestBody UserModel userModel) {

        //this should not be here ?
        HashMap<String, Object> response = new HashMap<>();

        if(this.userService.createUser(userModel)) {

            return AppResponse.success()
                    .withMessage("User created successfully")
                    .build();
        }

        return AppResponse.error()
                .withMessage("User could not be created")
                .build();
    }

    //GET ALL
    @GetMapping("/users")
    public ResponseEntity<?> fetchAllUsers() {

        ArrayList<UserModel> collection = (ArrayList<UserModel>) this.userService.getAllUsers();

        return AppResponse.success()
                .withData(collection)
                .build();
    }

    // GET ONE
    @GetMapping("/users/{id}")
    public ResponseEntity<?> fetchSingleUser(@PathVariable int id) {

        UserModel responseUser =  this.userService.getUser(id);

        if(responseUser == null) {
            return AppResponse.error()
                    .withMessage("User data not found")
                    .build();
        }

        return AppResponse.success()
                .withDataAsArray(responseUser)
                .build();
    }
    //by username
    @PostMapping("/login")
    public ResponseEntity<?> fetchSingleUsername(@RequestBody UserModel user) {

        UserModel responseUser =  this.userService.getUserByUsername(user.getUsername());

        if(responseUser == null) {
            return AppResponse.error()
                    .withMessage("User data not found")
                    .build();
        }

        return AppResponse.success()
                .withDataAsArray(responseUser)
                .build();
    }




    //UPDATE

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserModel user,@PathVariable int id) {
        boolean isUpdateSuccessful =  this.userService.updateUser(user,id);

        if(!isUpdateSuccessful) {
            return AppResponse.error()
                    .withMessage("user data not found")
                    .build();
        }

        return AppResponse.success()
                .withMessage("user successful")
                .build();
    }



    //DELETE SOFT
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> removeUser(@PathVariable int id) {

        boolean isUpdateSuccessful =  this.userService.removeUser(id);

        if(!isUpdateSuccessful) {
            return AppResponse.error()
                    .withMessage("user not found")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Remove successful")
                .build();
    }

}

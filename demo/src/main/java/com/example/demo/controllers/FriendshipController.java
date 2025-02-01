package com.example.demo.controllers;


import com.example.demo.http.AppResponse;
import com.example.demo.models.FriendshipModel;
import com.example.demo.models.UserModel;
import com.example.demo.repos.UserRepository;
import com.example.demo.services.FriendshipService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/friendships")
public class FriendshipController {

    //make a constructor later
    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/{userId}")        //empty=500
    public ResponseEntity<?> getFriendships(@PathVariable Integer userId) {


        List<FriendshipModel> dtoTest = this.friendshipService.getAllFriendshipsForUser(userId);


        return AppResponse.success()
                .withData(dtoTest)
                .build();
    }



    @PostMapping("/add/{userId}/{friendId}")
    public ResponseEntity<?> addFriendship(@PathVariable Integer userId, @PathVariable Integer friendId) {

        Optional<UserModel> currentUserOptional = userRepository.findById(userId);
        Optional<UserModel> addedFriendOptional = userRepository.findById(friendId);

        // Check if both users exist......i dont like this
        if (currentUserOptional.isPresent() && addedFriendOptional.isPresent()) {
            UserModel currentUser = currentUserOptional.get();
            UserModel addedFriend = addedFriendOptional.get();

            if (friendshipService.createFriendship(currentUser, addedFriend)) {
                return AppResponse.success()
                        .withMessage("Friend req sent")
                        .build();
            }
            return AppResponse.error()
                    .withMessage("Friend req error")
                    .build();
        } else {
            return AppResponse.error()
                    .withMessage("One or both users not found")
                    .build();
        }
    }


    //accept friend



}

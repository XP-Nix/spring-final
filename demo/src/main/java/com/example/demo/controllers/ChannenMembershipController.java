package com.example.demo.controllers;


import com.example.demo.dtoTEST.JoinRequest;
import com.example.demo.http.AppResponse;
import com.example.demo.models.ChannelMembershipModel;
import com.example.demo.models.ChannelModel;
import com.example.demo.models.UserModel;
import com.example.demo.services.ChannelMembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/api/memberships")
public class ChannenMembershipController {

    private final ChannelMembershipService membershipService;


    public ChannenMembershipController(ChannelMembershipService membershipService) {
        this.membershipService = membershipService;
    }


    //CREATE
    @PostMapping
//    public ResponseEntity<?> addMember(@RequestParam Integer channelId, @RequestParam Integer userId, @RequestParam(defaultValue = "GUEST") String role) {
    public ResponseEntity<?> addMember(@RequestBody JoinRequest request) {


        if(this.membershipService.addMember(request.getChannelId(), request.getUserId(), request.getRole())) {

            return AppResponse.success()
                    .withMessage("member added successfully")
                    .build();
        }

        return AppResponse.error()
                .withMessage("member could not be added")
                .build();

    }

    //works but returns too much useless info, might need a dto to clean it up later
    //GET
    @GetMapping("/channel/{channelId}")
    public ResponseEntity<?> getMembersByChannel(@PathVariable Integer channelId) {

        ArrayList<ChannelMembershipModel> collection = (ArrayList<ChannelMembershipModel>) this.membershipService.getMembersByChannel(channelId);

        return AppResponse.success()
                .withData(collection)
                .build();

    }


//UPDATE

    @PutMapping
    public ResponseEntity<?> updateMembers(@RequestParam Integer channelid,@RequestParam Integer userid ,@RequestParam String role) {


        boolean isUpdateSuccessful =  this.membershipService.updateRole(channelid,userid,role);

        if(!isUpdateSuccessful) {
            return AppResponse.error()
                    .withMessage("channel data not found")
                    .build();
        }

        return AppResponse.success()
                .withMessage("channel successful")
                .build();
    }







    //DELETE
    @DeleteMapping
    public ResponseEntity<?> removeMember(@RequestParam Integer channelId, @RequestParam Integer userId) {


        if(!this.membershipService.removeMember(channelId, userId)) {
            return AppResponse.error()
                    .withMessage("user not found")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Remove successful")
                .build();


    }

}

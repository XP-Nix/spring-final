package com.example.demo.controllers;


import com.example.demo.dtoTEST.JoinRequest;
import com.example.demo.http.AppResponse;
import com.example.demo.models.ChannelMembershipModel;
import com.example.demo.models.ChannelModel;
import com.example.demo.models.UserModel;
import com.example.demo.services.ChannelMembershipService;
import com.example.demo.services.ChannelService;
import com.example.demo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/api/memberships")
public class ChannenMembershipController {

    private final ChannelMembershipService membershipService;
    private final UserService userService;
    private final ChannelService channelService;


    public ChannenMembershipController(ChannelMembershipService membershipService, UserService userService, ChannelService channelService) {
        this.membershipService = membershipService;
        this.userService = userService;
        this.channelService = channelService;
    }


    //CREATE
    @PostMapping
//    public ResponseEntity<?> addMember(@RequestParam Integer channelId, @RequestParam Integer userId, @RequestParam(defaultValue = "GUEST") String role) {
    public ResponseEntity<?> addMember(@RequestBody JoinRequest request,@RequestParam Integer currentUserId) {



        if (this.membershipService.hasRole(currentUserId, request.getChannelId(), "GUEST")) {
            return AppResponse.error()
                    .withMessage("Users with GUEST role cannot add other members.")
                    .build();
        }

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
    public ResponseEntity<?> updateMembers(@RequestParam Integer channelId,
                                           @RequestParam Integer userId,
                                           @RequestParam String role,
                                           @RequestParam Integer currentUserId) {

        UserModel currentUser = this.userService.getUser(currentUserId);
        ChannelModel channel = this.channelService.getChannelById(channelId);


        // check owner
        if (!currentUser.equals(channel.getOwner())) {
            return AppResponse.error()
                    .withMessage("Only the owner can update guests from this channel")
                    .build();
        }


        boolean isUpdateSuccessful = this.membershipService.updateRole(channelId, userId, role);

        if (!isUpdateSuccessful) {
            return AppResponse.error()
                    .withMessage("Channel data not found or role update failed.")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Channel role updated successfully.")
                .build();
    }








    //DELETE
//    @DeleteMapping
//    public ResponseEntity<?> removeMember(@RequestParam Integer channelId, @RequestParam Integer userId) {
//
//
//        if(!this.membershipService.removeMember(channelId, userId)) {
//            return AppResponse.error()
//                    .withMessage("user not found")
//                    .build();
//        }
//
//        return AppResponse.success()
//                .withMessage("Remove successful")
//                .build();
//
//
//    }
    //remove per exam request
    @DeleteMapping("/{channelId}")
    public ResponseEntity<?> removeGuestFromChannel(
            @PathVariable Integer channelId,
            @RequestParam Integer userId,
            @RequestParam Integer guestId) {

        UserModel user = this.userService.getUser(userId);
        UserModel guest = this.userService.getUser(guestId);
        ChannelModel channel = this.channelService.getChannelById(channelId);

        // validate
        if (user == null || guest == null || channel == null) {
            return AppResponse.error()
                    .withMessage("Invalid owner, guest, or channel")
                    .build();
        }

        // check owner
        if (!user.equals(channel.getOwner())) {
            return AppResponse.error()
                    .withMessage("Only the owner can remove guests from this channel")
                    .build();
        }

        // check guest
        ChannelMembershipModel membership = this.membershipService.getMembership(channelId, guestId);
        if (membership == null || !membership.getRole().equals("GUEST")) {
            return AppResponse.error()
                    .withMessage("User is not a guest in this channel")
                    .build();
        }


        this.membershipService.removeMember(channelId, guestId);

        return AppResponse.success()
                .withMessage("Remove successful")
                .build();
    }

}

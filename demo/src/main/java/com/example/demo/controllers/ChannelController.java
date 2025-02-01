package com.example.demo.controllers;


import com.example.demo.http.AppResponse;
import com.example.demo.models.ChannelModel;
import com.example.demo.models.UserModel;
import com.example.demo.services.ChannelMembershipService;
import com.example.demo.services.ChannelService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/channels")
public class ChannelController {


    private final ChannelService channelService;
    private final ChannelMembershipService channelMembershipService;
    private final UserService userService;

    //@Autowired
    public ChannelController(ChannelService channelService, ChannelMembershipService channelMembershipService, UserService userService) {
        this.channelService = channelService;
        this.channelMembershipService = channelMembershipService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createChannel(@RequestBody ChannelModel channel) {


        if (channelService.createChannel(channel)){

            this.channelMembershipService.addMember(channel.getId(),channel.getOwner().getId(),"ADMIN");


            return AppResponse.success()
                    .withMessage("Channel created successfully")
                    .build();
        }

        return AppResponse.error()
                .withMessage("Channel could not be created")
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAllChannels() {
        ArrayList<ChannelModel> collection = (ArrayList<ChannelModel>) this.channelService.getAllChannels();

        return AppResponse.success()
                .withData(collection)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChannelById(@PathVariable Integer id) {
        ChannelModel responseChannel =  this.channelService.getChannelById(id);

        if(responseChannel == null) {
            return AppResponse.error()
                    .withMessage("Channel data not found")
                    .build();
        }

        return AppResponse.success()
                .withDataAsArray(responseChannel)
                .build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateChannel(@PathVariable Integer id, @RequestBody ChannelModel channelDetails) {

        boolean isUpdateSuccessful =  this.channelService.updateChannel(id,channelDetails);

        if(!isUpdateSuccessful) {
            return AppResponse.error()
                    .withMessage("channel data not found")
                    .build();
        }

        return AppResponse.success()
                .withMessage("channel successful")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChannel(@PathVariable Integer id,@RequestParam Integer userid) {
        UserModel currentUser = this.userService.getUser(userid);
        ChannelModel currentChannel = this.channelService.getChannelById(id);


        if (currentUser == null || !currentUser.equals(currentChannel.getOwner())) {
            return AppResponse.error()
                    .withMessage("Only the owner can delete the channel")
                    .build();
        }

        boolean isUpdateSuccessful =  this.channelService.deleteChannel(id);

        if(!isUpdateSuccessful) {
            return AppResponse.error()
                    .withMessage("channel not found")
                    .build();
        }

        return AppResponse.success()
                .withMessage("channel successful")
                .build();
    }
}

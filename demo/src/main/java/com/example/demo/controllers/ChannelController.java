package com.example.demo.controllers;


import com.example.demo.http.AppResponse;
import com.example.demo.models.ChannelModel;
import com.example.demo.services.ChannelMembershipService;
import com.example.demo.services.ChannelService;
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

    //@Autowired
    public ChannelController(ChannelService channelService, ChannelMembershipService channelMembershipService) {
        this.channelService = channelService;
        this.channelMembershipService = channelMembershipService;
    }

    @PostMapping
    public ResponseEntity<?> createChannel(@RequestBody ChannelModel channel) {

        // if you create a channel you are the owner
        //call join channel and change role
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
    public ResponseEntity<?> deleteChannel(@PathVariable Integer id) {
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

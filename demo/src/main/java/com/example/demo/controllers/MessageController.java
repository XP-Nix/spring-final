package com.example.demo.controllers;


import com.example.demo.http.AppResponse;
import com.example.demo.models.MessageModel;
import com.example.demo.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;


    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestParam Integer senderId, @RequestParam(required = false) Integer channelId, @RequestParam(required = false) Integer recipientId, @RequestBody String content) {



        if(messageService.sendMessage(senderId, channelId, recipientId, content)) {

            return AppResponse.success()
                    .withMessage("User created successfully")
                    .build();
        }

        return AppResponse.error()
                .withMessage("User could not be created")
                .build();

    }

    @GetMapping("/tochannel/{channelId}")
    public List<MessageModel> getChannelMessages(@PathVariable Integer channelId) {
        return messageService.getChannelMessages(channelId);
    }

    //@GetMapping("/direct")
    //public List<MessageModel> getDirectMessages(@RequestParam Integer userId1, @RequestParam Integer userId2) {
    //    return messageService.getDirectMessages(userId1, userId2);
    //}


}

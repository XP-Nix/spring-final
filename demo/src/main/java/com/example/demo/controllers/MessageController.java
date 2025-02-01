package com.example.demo.controllers;


import com.example.demo.http.AppResponse;
import com.example.demo.models.MessageModel;
import com.example.demo.services.ChannelMembershipService;
import com.example.demo.services.FriendshipService;
import com.example.demo.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final FriendshipService friendshipService;
    private final ChannelMembershipService channelMembershipService;


    public MessageController(MessageService messageService, FriendshipService friendshipService, ChannelMembershipService channelMembershipService) {
        this.messageService = messageService;
        this.friendshipService = friendshipService;
        this.channelMembershipService = channelMembershipService;
    }


    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestParam Integer senderId,
                                         @RequestParam(required = false) Integer recipientId,
                                         @RequestParam(required = false) Integer channelId,
                                         @RequestBody String content) {

        // Ensure either recipientId or channelId is provided, but not both
        if ((recipientId == null && channelId == null) || (recipientId != null && channelId != null)) {
            return AppResponse.error()
                    .withMessage("You must specify either a recipient or a channel, but not both.")
                    .build();
        }

        //if they users are friends
        if (recipientId != null) {
            if (!friendshipService.areFriends(senderId, recipientId)) {
                return AppResponse.error()
                        .withMessage("You can only send messages to friends.")
                        .build();
            }
        }

        //if the sender is a channel member
        if (channelId != null) {
            if (!channelMembershipService.isMember(senderId, channelId)) {
                return AppResponse.error()
                        .withMessage("You can only send messages in channels you are a member of.")
                        .build();
            }
        }

        // Send the message
        if (messageService.sendMessage(senderId, channelId, recipientId, content)) {
            return AppResponse.success()
                    .withMessage("Message sent successfully")
                    .build();
        }

        return AppResponse.error()
                .withMessage("Message could not be sent")
                .build();
    }


//    @GetMapping("/tochannel/{channelId}")
//    public List<MessageModel> getChannelMessages(@PathVariable Integer channelId) {
//        return messageService.getChannelMessages(channelId);
//    }

    //@GetMapping("/direct")
    //public List<MessageModel> getDirectMessages(@RequestParam Integer userId1, @RequestParam Integer userId2) {
    //    return messageService.getDirectMessages(userId1, userId2);
    //}


    @GetMapping
    public ResponseEntity<?> getMessages(@RequestParam Integer userId,
                                         @RequestParam Integer friendId) {

        // Check if users are friends
        if (!friendshipService.areFriends(userId, friendId)) {
            return AppResponse.error()
                    .withMessage("You can only view messages exchanged with friends.")
                    .build();
        }

        // Fetch messages
        List<MessageModel> messages = messageService.getMessagesWithFriend(userId, friendId);

        return ResponseEntity.ok(messages);
    }
    @GetMapping("/channel")
    public ResponseEntity<?> getMessagesInChannel(@RequestParam Integer userId,
                                                  @RequestParam Integer channelId) {

        //is user member
        if (!channelMembershipService.isMember(userId, channelId)) {
            return AppResponse.error()
                    .withMessage("You can only view messages in channels you are a member of.")
                    .build();
        }


        List<MessageModel> messages = messageService.getChannelMessages(channelId);

        return ResponseEntity.ok(messages);
    }


}

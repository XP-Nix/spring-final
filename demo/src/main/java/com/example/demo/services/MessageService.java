package com.example.demo.services;

import com.example.demo.models.ChannelModel;
import com.example.demo.models.MessageModel;
import com.example.demo.models.UserModel;
import com.example.demo.repos.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChannelService channelService;
    private final UserService userService;

    public MessageService(MessageRepository messageRepository, ChannelService channelService, UserService userService) {
        this.messageRepository = messageRepository;
        this.channelService = channelService;
        this.userService = userService;
    }




    public boolean sendMessage(Integer senderId, Integer channelId, Integer recipientId, String content) {

        UserModel sender = userService.getUser(senderId);

        //receive
        ChannelModel channel = channelId != null ? channelService.getChannelById(channelId) : null;
        UserModel recipient = recipientId != null ? userService.getUser(recipientId) : null;

        if (channel == null && recipient == null) {
            throw new RuntimeException("message was not received");
        }

        //build
        MessageModel message = new MessageModel();
        message.setSender(sender);
        message.setChannel(channel);
        message.setRecipient(recipient);
        message.setContent(content);

        messageRepository.save(message);

        return true;
    }


    public List<MessageModel> getChannelMessages(Integer channelId) {
        return messageRepository.findByChannelId(channelId);
    }

    public List<MessageModel> getMessagesWithFriend(Integer userId, Integer friendId) {
        return messageRepository.findBySenderIdAndRecipientId(userId, friendId);
    }

    //public List<MessageModel> getDirectMessages(Integer userId1, Integer userId2) {
    //    return messageRepository.findBySenderIdAndRecipientIdOrRecipientIdAndSenderIdOrderBySentAtAsc(userId1, userId2, userId1, userId2);
    //}



}

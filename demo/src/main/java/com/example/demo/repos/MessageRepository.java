package com.example.demo.repos;


import com.example.demo.models.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageModel,Integer> {

    List<MessageModel> findByChannelId(Integer channelId);

    //direct messages between two users
    //List<MessageModel> findBySenderIdAndRecipientIdOrRecipientIdAndSenderIdOrderBySentAtAsc(
    //        Integer senderId, Integer recipientId, Integer recipientId2, Integer senderId2);

}

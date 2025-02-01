package com.example.demo.repos;


import com.example.demo.models.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageModel,Integer> {

    List<MessageModel> findByChannelId(Integer channelId);

//    @Query("SELECT m FROM Message m WHERE " +
//            "(m.senderId = :userId AND m.recipientId = :friendId) OR " +
//            "(m.senderId = :friendId AND m.recipientId = :userId) " +
//            "ORDER BY m.timestamp ASC")
//    List<Message> findMessagesBetweenUsers(@Param("userId") Integer userId,
//                                           @Param("friendId") Integer friendId);

    //direct messages between two users
    List<MessageModel> findBySenderIdAndRecipientId(
            Integer senderId, Integer recipientId);

}

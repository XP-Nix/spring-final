package com.example.demo.services;

import com.example.demo.models.FriendshipModel;
import com.example.demo.models.UserModel;
import com.example.demo.repos.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipService {


    private final FriendshipRepository friendshipRepository;

    public FriendshipService(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }


    public List<FriendshipModel> getAllFriendshipsForUser(Integer userId) {
        return this.friendshipRepository.findByUserId(userId);
    }


    //wanted empty constructor, might cause problem later
    public boolean createFriendship(UserModel user, UserModel friend) {
        FriendshipModel friendship = new FriendshipModel();
        friendship.setUser(user);
        friendship.setFriend(friend);
        friendship.setStatus("pending");
        friendshipRepository.save(friendship);

        System.out.println("debug/FriendshipService/24");
        return true;
    }



    //for the status, for now
    public FriendshipModel updateFriendshipStatus(Integer userId, Integer friendId, String status) {
        FriendshipModel friendship = friendshipRepository.findByUserIdAndFriendId(userId, friendId);
        if (friendship != null) {
            friendship.setStatus(status);
            return friendshipRepository.save(friendship);
        }
        throw new IllegalArgumentException("Friendship not found");
    }

}

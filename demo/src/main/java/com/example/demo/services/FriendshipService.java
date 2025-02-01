package com.example.demo.services;

import com.example.demo.models.FriendshipModel;
import com.example.demo.models.UserModel;
import com.example.demo.repos.FriendshipRepository;
import com.example.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendshipService {


    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;


    public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
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

    public boolean areFriends(Integer userId1, Integer userId2) {
        // Retrieve UserModel objects from the database
        Optional<UserModel> user1 = userRepository.findById(userId1);
        Optional<UserModel> user2 = userRepository.findById(userId2);

        if (user1.isEmpty() || user2.isEmpty()) {
            return false;
        }

        // Check if they are friends in either direction
        return friendshipRepository.existsByUserAndFriend(user1.get(), user2.get()) ||
                friendshipRepository.existsByUserAndFriend(user2.get(), user1.get());
    }
}

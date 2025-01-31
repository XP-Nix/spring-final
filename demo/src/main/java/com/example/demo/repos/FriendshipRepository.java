package com.example.demo.repos;

import com.example.demo.models.FriendshipModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<FriendshipModel, Long> {
    // Find friendships for a specific user
    List<FriendshipModel> findByUserId(Integer userId);

    // Find friendships by both user and friend to handle specific queries
    FriendshipModel findByUserIdAndFriendId(Integer userId, Integer friendId);
}

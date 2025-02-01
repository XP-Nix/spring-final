package com.example.demo.repos;

import com.example.demo.models.FriendshipModel;
import com.example.demo.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<FriendshipModel, Long> {

    List<FriendshipModel> findByUserId(Integer userId);

    FriendshipModel findByUserIdAndFriendId(Integer userId, Integer friendId);

    boolean existsByUserAndFriend(UserModel userModel, UserModel userModel1);
}

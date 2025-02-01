package com.example.demo.repos;

import com.example.demo.models.ChannelMembershipModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelMembershipRepository extends JpaRepository<ChannelMembershipModel, Integer> {


    List<ChannelMembershipModel> findByChannelId(Integer channelId);

    List<ChannelMembershipModel> findByUserId(Integer userId);

    ChannelMembershipModel findByChannelIdAndUserId(Integer channelId, Integer userId);

    boolean existsByUserIdAndChannelId(Integer userId, Integer channelId);

    ChannelMembershipModel findByUserIdAndChannelId(Integer userId, Integer channelId);
}

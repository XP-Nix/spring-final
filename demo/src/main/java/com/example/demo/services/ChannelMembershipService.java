package com.example.demo.services;


import com.example.demo.models.ChannelMembershipModel;
import com.example.demo.models.ChannelModel;
import com.example.demo.models.UserModel;
import com.example.demo.repos.ChannelMembershipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelMembershipService {

    private final ChannelMembershipRepository membershipRepository;
    private final ChannelService channelService; // To verify channels exist
    private final UserService userService;       // To verify users exist

    public ChannelMembershipService(ChannelMembershipRepository membershipRepository, ChannelService channelService, UserService userService) {
        this.membershipRepository = membershipRepository;
        this.channelService = channelService;
        this.userService = userService;
    }



    public boolean addMember(Integer channelId, Integer userId, String role) {

        ChannelModel channel = channelService.getChannelById(channelId);
        UserModel user = userService.getUser(userId);

        // validdation not important for now
        if (membershipRepository.findByChannelIdAndUserId(channelId, userId) != null) {
            throw new RuntimeException("User is already a member of this channel");
        }



        ChannelMembershipModel membership = new ChannelMembershipModel();
        membership.setChannel(channel);
        membership.setUser(user);
        membership.setRole(role);

        this.membershipRepository.save(membership);

        return true;
    }

    public List<ChannelMembershipModel> getMembersByChannel(Integer channelId) {
        return membershipRepository.findByChannelId(channelId);
    }

    public List<ChannelMembershipModel> getChannelsForUser(Integer userId) {
        return membershipRepository.findByUserId(userId);
    }




    public boolean removeMember(Integer channelId, Integer userId) {

        ChannelMembershipModel membership = membershipRepository.findByChannelIdAndUserId(channelId, userId);
        if (membership == null) {
            throw new RuntimeException("Membership not found");
        }
        membershipRepository.delete(membership);
        return true;

    }





    //todo
    public boolean updateRole(Integer channelId, Integer userId, String newRole) {

        ChannelMembershipModel membership = membershipRepository.findByChannelIdAndUserId(channelId, userId);
        if (membership == null) {
            throw new RuntimeException("membership not found");
        }
        membership.setRole(newRole);
        membershipRepository.save(membership);

        return true;
    }


    public ChannelMembershipModel getMembership(Integer channelId, Integer userId) {
        return membershipRepository.findByChannelIdAndUserId(channelId, userId);
    }

    public boolean isMember(Integer userId, Integer channelId) {
        return membershipRepository.existsByUserIdAndChannelId(userId, channelId);
    }

    public boolean hasRole(Integer userId, Integer channelId, String role) {
        ChannelMembershipModel membership = membershipRepository.findByUserIdAndChannelId(userId, channelId);
        return membership != null && membership.getRole().equals(role);
    }
}

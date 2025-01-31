package com.example.demo.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "td_channel_memberships")
public class ChannelMembershipModel {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "channel_id", nullable = false)
    private ChannelModel channel;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(nullable = false)
    private String role = "GUEST";

    @Column(name = "joined_at", updatable = false)
    private LocalDateTime joinedAt = LocalDateTime.now();


    public ChannelMembershipModel(Integer id, ChannelModel channel, UserModel user, String role, LocalDateTime joinedAt) {
        this.id = id;
        this.channel = channel;
        this.user = user;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    public ChannelMembershipModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ChannelModel getChannel() {
        return channel;
    }

    public void setChannel(ChannelModel channel) {
        this.channel = channel;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}


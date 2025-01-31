package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "td_friendships")
public class FriendshipModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private UserModel friend;




    @Column(name = "status", nullable = false, columnDefinition = "ENUM('pending', 'accepted', 'blocked') DEFAULT 'pending'")
    private String status = "pending";

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


    public FriendshipModel(Integer id, UserModel user, UserModel friend, String status, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.friend = friend;
        this.status = status;
        this.createdAt = createdAt;
    }

    public FriendshipModel() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserModel getFriend() {
        return friend;
    }

    public void setFriend(UserModel friend) {
        this.friend = friend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

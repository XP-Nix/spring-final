package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "td_users")
@Getter
@Setter
@NoArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "is_deleted", nullable = false)
    private int isDeleted = 0;



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore //fixes infinite recursion ∩ （^o^） ∩
    private Set<FriendshipModel> friendships;

    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<FriendshipModel> friendOf;


    public UserModel(Integer id, String username, String password, int isDeleted, Set<FriendshipModel> friendships, Set<FriendshipModel> friendOf) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isDeleted = isDeleted;
        this.friendships = friendships;
        this.friendOf = friendOf;
    }



    public Set<FriendshipModel> getFriendships() {
        return friendships;
    }

    public void setFriendships(Set<FriendshipModel> friendships) {
        this.friendships = friendships;
    }

    public Set<FriendshipModel> getFriendOf() {
        return friendOf;
    }

    public void setFriendOf(Set<FriendshipModel> friendOf) {
        this.friendOf = friendOf;
    }
//    public UserModel(Integer id, String username, String password, int isDeleted) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.isDeleted = isDeleted;
//    }

    //lombok doesnt work for some reason
    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int status) {
        isDeleted = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

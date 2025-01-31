package com.example.demo.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "td_messages")
public class MessageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private ChannelModel channel;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserModel sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private UserModel recipient; // For private messages

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "sent_at", updatable = false)
    private LocalDateTime sentAt = LocalDateTime.now();

    public MessageModel(Integer id, ChannelModel channel, UserModel sender, UserModel recipient, String content, LocalDateTime sentAt) {
        this.id = id;
        this.channel = channel;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.sentAt = sentAt;
    }

    public MessageModel() {
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

    public UserModel getSender() {
        return sender;
    }

    public void setSender(UserModel sender) {
        this.sender = sender;
    }

    public UserModel getRecipient() {
        return recipient;
    }

    public void setRecipient(UserModel recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}


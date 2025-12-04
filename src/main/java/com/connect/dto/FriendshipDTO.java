package com.connect.dto;

import com.connect.model.Friendship;

public class FriendshipDTO {
    
    private Long id;
    private UserDTO requester;
    private UserDTO addressee;
    private String status;
    private String createdAt;
    
    public FriendshipDTO() {}
    
    public FriendshipDTO(Friendship friendship) {
        this.id = friendship.getId();
        this.requester = new UserDTO(friendship.getRequester());
        this.addressee = new UserDTO(friendship.getAddressee());
        this.status = friendship.getStatus().name();
        this.createdAt = friendship.getCreatedAt() != null ? friendship.getCreatedAt().toString() : null;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public UserDTO getRequester() {
        return requester;
    }
    
    public void setRequester(UserDTO requester) {
        this.requester = requester;
    }
    
    public UserDTO getAddressee() {
        return addressee;
    }
    
    public void setAddressee(UserDTO addressee) {
        this.addressee = addressee;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

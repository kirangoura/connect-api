package com.connect.dto;

import com.connect.model.User;

public class UserDiscoverDTO {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String avatarUrl;
    private boolean isFriend;
    private boolean pendingRequest;
    
    public UserDiscoverDTO() {}
    
    public UserDiscoverDTO(User user, boolean isFriend, boolean pendingRequest) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.avatarUrl = user.getAvatarUrl();
        this.isFriend = isFriend;
        this.pendingRequest = pendingRequest;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAvatarUrl() {
        return avatarUrl;
    }
    
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    
    public boolean isFriend() {
        return isFriend;
    }
    
    public void setFriend(boolean friend) {
        isFriend = friend;
    }
    
    public boolean isPendingRequest() {
        return pendingRequest;
    }
    
    public void setPendingRequest(boolean pendingRequest) {
        this.pendingRequest = pendingRequest;
    }
}

package com.connect.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import java.time.LocalDateTime;

@Entity
@Table(name = "friendships")
public class Friendship {
    
    public enum Status {
        pending, accepted, rejected
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressee_id", nullable = false)
    private User addressee;
    
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "friendship_status")
    @Type(PostgreSQLEnumType.class)
    private Status status = Status.pending;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public Friendship() {}
    
    public Friendship(User requester, User addressee) {
        this.requester = requester;
        this.addressee = addressee;
        this.status = Status.pending;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getRequester() {
        return requester;
    }
    
    public void setRequester(User requester) {
        this.requester = requester;
    }
    
    public User getAddressee() {
        return addressee;
    }
    
    public void setAddressee(User addressee) {
        this.addressee = addressee;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

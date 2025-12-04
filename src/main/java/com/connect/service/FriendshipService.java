package com.connect.service;

import com.connect.dto.FriendshipDTO;
import com.connect.dto.UserDTO;
import com.connect.model.Friendship;
import com.connect.model.User;
import com.connect.repository.FriendshipRepository;
import com.connect.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendshipService {
    
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    
    public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }
    
    public FriendshipDTO sendFriendRequest(User requester, Long addresseeId) {
        if (requester.getId().equals(addresseeId)) {
            throw new RuntimeException("Cannot send friend request to yourself");
        }
        
        User addressee = userRepository.findById(addresseeId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (friendshipRepository.findFriendshipBetween(requester, addressee).isPresent()) {
            throw new RuntimeException("Friendship request already exists");
        }
        
        Friendship friendship = new Friendship(requester, addressee);
        friendship = friendshipRepository.save(friendship);
        
        return new FriendshipDTO(friendship);
    }
    
    @Transactional
    public FriendshipDTO acceptFriendRequest(User user, Long friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
            .orElseThrow(() -> new RuntimeException("Friend request not found"));
        
        if (!friendship.getAddressee().getId().equals(user.getId())) {
            throw new RuntimeException("Not authorized to accept this request");
        }
        
        if (friendship.getStatus() != Friendship.Status.PENDING) {
            throw new RuntimeException("Request is no longer pending");
        }
        
        friendship.setStatus(Friendship.Status.ACCEPTED);
        friendship = friendshipRepository.save(friendship);
        
        return new FriendshipDTO(friendship);
    }
    
    @Transactional
    public FriendshipDTO rejectFriendRequest(User user, Long friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
            .orElseThrow(() -> new RuntimeException("Friend request not found"));
        
        if (!friendship.getAddressee().getId().equals(user.getId())) {
            throw new RuntimeException("Not authorized to reject this request");
        }
        
        if (friendship.getStatus() != Friendship.Status.PENDING) {
            throw new RuntimeException("Request is no longer pending");
        }
        
        friendship.setStatus(Friendship.Status.REJECTED);
        friendship = friendshipRepository.save(friendship);
        
        return new FriendshipDTO(friendship);
    }
    
    public List<UserDTO> getFriends(User user) {
        List<Friendship> friendships = friendshipRepository.findAcceptedFriendships(user);
        
        return friendships.stream()
            .map(f -> {
                User friend = f.getRequester().getId().equals(user.getId()) 
                    ? f.getAddressee() 
                    : f.getRequester();
                return new UserDTO(friend);
            })
            .collect(Collectors.toList());
    }
    
    public List<FriendshipDTO> getPendingRequests(User user) {
        return friendshipRepository.findPendingRequestsForUser(user).stream()
            .map(FriendshipDTO::new)
            .collect(Collectors.toList());
    }
    
    public List<FriendshipDTO> getSentRequests(User user) {
        return friendshipRepository.findSentRequestsByUser(user).stream()
            .map(FriendshipDTO::new)
            .collect(Collectors.toList());
    }
    
    @Transactional
    public void removeFriend(User user, Long friendId) {
        User friend = userRepository.findById(friendId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Friendship friendship = friendshipRepository.findFriendshipBetween(user, friend)
            .orElseThrow(() -> new RuntimeException("Friendship not found"));
        
        friendshipRepository.delete(friendship);
    }
    
    public boolean areFriends(User user1, User user2) {
        return friendshipRepository.areFriends(user1, user2);
    }
}

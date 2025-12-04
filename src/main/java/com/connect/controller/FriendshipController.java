package com.connect.controller;

import com.connect.dto.FriendshipDTO;
import com.connect.dto.UserDTO;
import com.connect.model.User;
import com.connect.service.FriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friends")
public class FriendshipController {
    
    private final FriendshipService friendshipService;
    
    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }
    
    @GetMapping
    public ResponseEntity<List<UserDTO>> getFriends(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(friendshipService.getFriends(user));
    }
    
    @PostMapping("/request/{userId}")
    public ResponseEntity<FriendshipDTO> sendFriendRequest(
            @AuthenticationPrincipal User user,
            @PathVariable Long userId) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        FriendshipDTO friendship = friendshipService.sendFriendRequest(user, userId);
        return ResponseEntity.ok(friendship);
    }
    
    @GetMapping("/requests/pending")
    public ResponseEntity<List<FriendshipDTO>> getPendingRequests(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(friendshipService.getPendingRequests(user));
    }
    
    @GetMapping("/requests/sent")
    public ResponseEntity<List<FriendshipDTO>> getSentRequests(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(friendshipService.getSentRequests(user));
    }
    
    @PostMapping("/accept/{friendshipId}")
    public ResponseEntity<FriendshipDTO> acceptFriendRequest(
            @AuthenticationPrincipal User user,
            @PathVariable Long friendshipId) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        FriendshipDTO friendship = friendshipService.acceptFriendRequest(user, friendshipId);
        return ResponseEntity.ok(friendship);
    }
    
    @PostMapping("/reject/{friendshipId}")
    public ResponseEntity<FriendshipDTO> rejectFriendRequest(
            @AuthenticationPrincipal User user,
            @PathVariable Long friendshipId) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        FriendshipDTO friendship = friendshipService.rejectFriendRequest(user, friendshipId);
        return ResponseEntity.ok(friendship);
    }
    
    @DeleteMapping("/{friendId}")
    public ResponseEntity<Map<String, String>> removeFriend(
            @AuthenticationPrincipal User user,
            @PathVariable Long friendId) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        friendshipService.removeFriend(user, friendId);
        return ResponseEntity.ok(Map.of("message", "Friend removed successfully"));
    }
}

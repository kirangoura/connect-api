package com.connect.controller;

import com.connect.dto.UpdateProfileRequest;
import com.connect.dto.UserDTO;
import com.connect.dto.UserDiscoverDTO;
import com.connect.model.User;
import com.connect.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(new UserDTO(user));
    }
    
    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody UpdateProfileRequest request) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        UserDTO updatedUser = userService.updateProfile(user, request);
        return ResponseEntity.ok(updatedUser);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/discover")
    public ResponseEntity<List<UserDiscoverDTO>> discoverUsers(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        List<UserDiscoverDTO> users = userService.discoverUsers(user, page, size);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<UserDiscoverDTO>> searchUsers(
            @AuthenticationPrincipal User user,
            @RequestParam String q) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        if (q == null || q.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<UserDiscoverDTO> users = userService.searchUsers(user, q.trim());
        return ResponseEntity.ok(users);
    }
}

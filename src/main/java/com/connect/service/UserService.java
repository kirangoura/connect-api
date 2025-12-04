package com.connect.service;

import com.connect.dto.UpdateProfileRequest;
import com.connect.dto.UserDTO;
import com.connect.dto.UserDiscoverDTO;
import com.connect.model.User;
import com.connect.repository.FriendshipRepository;
import com.connect.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    
    public UserService(UserRepository userRepository, FriendshipRepository friendshipRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
    }
    
    public UserDTO updateProfile(User user, UpdateProfileRequest request) {
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }
        
        user = userRepository.save(user);
        return new UserDTO(user);
    }
    
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(UserDTO::new);
    }
    
    public List<UserDiscoverDTO> discoverUsers(User currentUser, int page, int size) {
        Long userId = currentUser.getId();
        
        Set<Long> friendIds = friendshipRepository.findFriendIds(userId);
        Set<Long> pendingIds = friendshipRepository.findPendingRequestUserIds(userId);
        
        Set<Long> excludeIds = new HashSet<>();
        excludeIds.add(userId);
        excludeIds.addAll(friendIds);
        excludeIds.addAll(pendingIds);
        
        if (excludeIds.isEmpty()) {
            excludeIds.add(-1L);
        }
        
        Page<User> usersPage = userRepository.findUsersExcluding(
            new ArrayList<>(excludeIds), 
            PageRequest.of(page, size)
        );
        
        return usersPage.getContent().stream()
            .map(user -> new UserDiscoverDTO(user, false, false))
            .collect(Collectors.toList());
    }
    
    public List<UserDiscoverDTO> searchUsers(User currentUser, String query) {
        Long userId = currentUser.getId();
        
        Set<Long> friendIds = friendshipRepository.findFriendIds(userId);
        Set<Long> pendingIds = friendshipRepository.findPendingRequestUserIds(userId);
        
        List<Long> excludeIds = Collections.singletonList(userId);
        
        List<User> users = userRepository.searchUsers(query, excludeIds);
        
        return users.stream()
            .map(user -> {
                boolean isFriend = friendIds.contains(user.getId());
                boolean pendingRequest = pendingIds.contains(user.getId());
                return new UserDiscoverDTO(user, isFriend, pendingRequest);
            })
            .collect(Collectors.toList());
    }
}

package com.connect.controller;

import com.connect.model.Event;
import com.connect.model.User;
import com.connect.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    
    private final FavoriteService favoriteService;
    
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }
    
    @GetMapping
    public ResponseEntity<List<Event>> getFavorites(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(favoriteService.getFavoriteEvents(user));
    }
    
    @PostMapping("/{eventId}")
    public ResponseEntity<Map<String, String>> addFavorite(
            @AuthenticationPrincipal User user,
            @PathVariable Long eventId) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        favoriteService.addFavorite(user, eventId);
        return ResponseEntity.ok(Map.of("message", "Event added to favorites"));
    }
    
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Map<String, String>> removeFavorite(
            @AuthenticationPrincipal User user,
            @PathVariable Long eventId) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        favoriteService.removeFavorite(user, eventId);
        return ResponseEntity.ok(Map.of("message", "Event removed from favorites"));
    }
    
    @GetMapping("/{eventId}/check")
    public ResponseEntity<Map<String, Boolean>> checkFavorite(
            @AuthenticationPrincipal User user,
            @PathVariable Long eventId) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        boolean isFavorite = favoriteService.isFavorite(user, eventId);
        return ResponseEntity.ok(Map.of("isFavorite", isFavorite));
    }
}

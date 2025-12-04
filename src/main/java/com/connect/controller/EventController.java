package com.connect.controller;

import com.connect.model.Event;
import com.connect.model.User;
import com.connect.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {
    
    private final EventService eventService;
    
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Event> createEvent(
            @AuthenticationPrincipal User user,
            @RequestBody Event event) {
        if (user != null) {
            return ResponseEntity.ok(eventService.createEvent(event, user));
        }
        return ResponseEntity.ok(eventService.createEvent(event));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return ResponseEntity.ok(eventService.updateEvent(id, event));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{id}/join")
    public ResponseEntity<Event> joinEvent(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        if (user != null) {
            return ResponseEntity.ok(eventService.joinEvent(id, user));
        }
        return ResponseEntity.ok(eventService.joinEvent(id));
    }
    
    @PostMapping("/{id}/leave")
    public ResponseEntity<Map<String, String>> leaveEvent(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        eventService.leaveEvent(id, user);
        return ResponseEntity.ok(Map.of("message", "Left event successfully"));
    }
    
    @GetMapping("/my")
    public ResponseEntity<List<Event>> getMyEvents(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(eventService.getMyEvents(user));
    }
    
    @GetMapping("/created")
    public ResponseEntity<List<Event>> getCreatedEvents(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(eventService.getCreatedEvents(user));
    }
    
    @GetMapping("/friends")
    public ResponseEntity<List<Event>> getFriendsEvents(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(eventService.getFriendsEvents(user));
    }
    
    @GetMapping("/{id}/joined")
    public ResponseEntity<Map<String, Boolean>> checkJoined(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.ok(Map.of("joined", false));
        }
        boolean joined = eventService.hasJoined(user, id);
        return ResponseEntity.ok(Map.of("joined", joined));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Event>> searchEvents(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(eventService.searchAndFilterEvents(location, category));
    }
}

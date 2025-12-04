package com.connect.service;

import com.connect.model.Event;
import com.connect.model.Favorite;
import com.connect.model.User;
import com.connect.repository.EventRepository;
import com.connect.repository.FavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteService {
    
    private final FavoriteRepository favoriteRepository;
    private final EventRepository eventRepository;
    
    public FavoriteService(FavoriteRepository favoriteRepository, EventRepository eventRepository) {
        this.favoriteRepository = favoriteRepository;
        this.eventRepository = eventRepository;
    }
    
    public Favorite addFavorite(User user, Long eventId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));
        
        if (favoriteRepository.existsByUserAndEvent(user, event)) {
            throw new RuntimeException("Event already in favorites");
        }
        
        Favorite favorite = new Favorite(user, event);
        return favoriteRepository.save(favorite);
    }
    
    @Transactional
    public void removeFavorite(User user, Long eventId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));
        
        favoriteRepository.deleteByUserAndEvent(user, event);
    }
    
    public List<Event> getFavoriteEvents(User user) {
        return favoriteRepository.findFavoriteEventsByUser(user);
    }
    
    public boolean isFavorite(User user, Long eventId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null) return false;
        return favoriteRepository.existsByUserAndEvent(user, event);
    }
}

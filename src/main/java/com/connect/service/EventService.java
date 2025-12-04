package com.connect.service;

import com.connect.model.Event;
import com.connect.model.EventAttendee;
import com.connect.model.User;
import com.connect.repository.EventAttendeeRepository;
import com.connect.repository.EventRepository;
import com.connect.repository.FriendshipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    
    private final EventRepository eventRepository;
    private final EventAttendeeRepository eventAttendeeRepository;
    private final FriendshipRepository friendshipRepository;
    
    public EventService(EventRepository eventRepository, 
                       EventAttendeeRepository eventAttendeeRepository,
                       FriendshipRepository friendshipRepository) {
        this.eventRepository = eventRepository;
        this.eventAttendeeRepository = eventAttendeeRepository;
        this.friendshipRepository = friendshipRepository;
    }
    
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }
    
    public Event createEvent(Event event, User creator) {
        event.setCreatorId(creator.getId());
        return eventRepository.save(event);
    }
    
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }
    
    public Event updateEvent(Long id, Event eventDetails) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setTitle(eventDetails.getTitle());
        event.setCategory(eventDetails.getCategory());
        event.setDescription(eventDetails.getDescription());
        event.setDate(eventDetails.getDate());
        event.setLocation(eventDetails.getLocation());
        event.setAttendees(eventDetails.getAttendees());
        return eventRepository.save(event);
    }
    
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
    
    public Event joinEvent(Long id) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found"));
        if (event.getAttendees() < event.getMaxAttendees()) {
            event.setAttendees(event.getAttendees() + 1);
            return eventRepository.save(event);
        }
        throw new RuntimeException("Event is full");
    }
    
    @Transactional
    public Event joinEvent(Long eventId, User user) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));
        
        if (eventAttendeeRepository.existsByEventAndUser(event, user)) {
            throw new RuntimeException("Already joined this event");
        }
        
        if (event.getAttendees() >= event.getMaxAttendees()) {
            throw new RuntimeException("Event is full");
        }
        
        EventAttendee attendee = new EventAttendee(event, user);
        eventAttendeeRepository.save(attendee);
        
        event.setAttendees(event.getAttendees() + 1);
        return eventRepository.save(event);
    }
    
    @Transactional
    public void leaveEvent(Long eventId, User user) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));
        
        if (!eventAttendeeRepository.existsByEventAndUser(event, user)) {
            throw new RuntimeException("Not joined this event");
        }
        
        eventAttendeeRepository.deleteByEventAndUser(event, user);
        
        if (event.getAttendees() > 0) {
            event.setAttendees(event.getAttendees() - 1);
            eventRepository.save(event);
        }
    }
    
    public List<Event> getMyEvents(User user) {
        return eventAttendeeRepository.findEventsByUser(user);
    }
    
    public List<Event> getCreatedEvents(User user) {
        return eventRepository.findByCreatorId(user.getId());
    }
    
    public List<Event> getFriendsEvents(User user) {
        var friendships = friendshipRepository.findAcceptedFriendships(user);
        
        return friendships.stream()
            .flatMap(f -> {
                User friend = f.getRequester().getId().equals(user.getId()) 
                    ? f.getAddressee() 
                    : f.getRequester();
                return eventAttendeeRepository.findEventsByUser(friend).stream();
            })
            .distinct()
            .collect(Collectors.toList());
    }
    
    public boolean hasJoined(User user, Long eventId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null) return false;
        return eventAttendeeRepository.existsByEventAndUser(event, user);
    }
    
    public List<Event> searchAndFilterEvents(String location, String category) {
        if (location != null && !location.trim().isEmpty() && category != null && !category.trim().isEmpty()) {
            return eventRepository.searchByLocationAndCategory(location, category);
        } else if (location != null && !location.trim().isEmpty()) {
            return eventRepository.searchByLocation(location);
        } else if (category != null && !category.trim().isEmpty()) {
            return eventRepository.findByCategory(category);
        } else {
            return eventRepository.findAll();
        }
    }
}

package com.connect.repository;

import com.connect.model.Event;
import com.connect.model.EventAttendee;
import com.connect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventAttendeeRepository extends JpaRepository<EventAttendee, Long> {
    
    List<EventAttendee> findByUser(User user);
    
    List<EventAttendee> findByEvent(Event event);
    
    Optional<EventAttendee> findByEventAndUser(Event event, User user);
    
    boolean existsByEventAndUser(Event event, User user);
    
    @Query("SELECT ea.event FROM EventAttendee ea WHERE ea.user = :user")
    List<Event> findEventsByUser(@Param("user") User user);
    
    @Query("SELECT COUNT(ea) FROM EventAttendee ea WHERE ea.event = :event")
    int countByEvent(@Param("event") Event event);
    
    void deleteByEventAndUser(Event event, User user);
}

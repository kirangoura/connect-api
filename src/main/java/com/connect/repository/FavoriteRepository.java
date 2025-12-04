package com.connect.repository;

import com.connect.model.Event;
import com.connect.model.Favorite;
import com.connect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    
    List<Favorite> findByUser(User user);
    
    Optional<Favorite> findByUserAndEvent(User user, Event event);
    
    boolean existsByUserAndEvent(User user, Event event);
    
    @Query("SELECT f.event FROM Favorite f WHERE f.user = :user")
    List<Event> findFavoriteEventsByUser(@Param("user") User user);
    
    void deleteByUserAndEvent(User user, Event event);
}

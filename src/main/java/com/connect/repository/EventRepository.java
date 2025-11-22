package com.connect.repository;

import com.connect.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCategory(String category);
    
    @Query("SELECT e FROM Event e WHERE (LOWER(e.city) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(e.area) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(e.zipcode) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Event> searchByLocation(@Param("search") String search);
    
    @Query("SELECT e FROM Event e WHERE e.category = :category AND " +
           "(LOWER(e.city) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(e.area) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(e.zipcode) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Event> searchByLocationAndCategory(@Param("search") String search, @Param("category") String category);
}

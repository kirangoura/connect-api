package com.connect.repository;

import com.connect.model.Friendship;
import com.connect.model.Friendship.Status;
import com.connect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    
    @Query("SELECT f FROM Friendship f WHERE " +
           "(f.requester = :user OR f.addressee = :user) AND f.status = :status")
    List<Friendship> findByUserAndStatus(@Param("user") User user, @Param("status") Status status);
    
    default List<Friendship> findAcceptedFriendships(User user) {
        return findByUserAndStatus(user, Status.ACCEPTED);
    }
    
    @Query("SELECT f FROM Friendship f WHERE f.addressee = :user AND f.status = :status")
    List<Friendship> findByAddresseeAndStatus(@Param("user") User user, @Param("status") Status status);
    
    default List<Friendship> findPendingRequestsForUser(User user) {
        return findByAddresseeAndStatus(user, Status.PENDING);
    }
    
    @Query("SELECT f FROM Friendship f WHERE f.requester = :user AND f.status = :status")
    List<Friendship> findByRequesterAndStatus(@Param("user") User user, @Param("status") Status status);
    
    default List<Friendship> findSentRequestsByUser(User user) {
        return findByRequesterAndStatus(user, Status.PENDING);
    }
    
    @Query("SELECT f FROM Friendship f WHERE " +
           "((f.requester = :user1 AND f.addressee = :user2) OR " +
           "(f.requester = :user2 AND f.addressee = :user1))")
    Optional<Friendship> findFriendshipBetween(@Param("user1") User user1, @Param("user2") User user2);
    
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Friendship f WHERE " +
           "((f.requester = :user1 AND f.addressee = :user2) OR " +
           "(f.requester = :user2 AND f.addressee = :user1)) AND f.status = :status")
    boolean areFriendsWithStatus(@Param("user1") User user1, @Param("user2") User user2, @Param("status") Status status);
    
    default boolean areFriends(User user1, User user2) {
        return areFriendsWithStatus(user1, user2, Status.ACCEPTED);
    }
    
    @Query("SELECT CASE WHEN f.requester.id = :userId THEN f.addressee.id ELSE f.requester.id END " +
           "FROM Friendship f WHERE (f.requester.id = :userId OR f.addressee.id = :userId) AND f.status = :status")
    Set<Long> findRelatedUserIdsByStatus(@Param("userId") Long userId, @Param("status") Status status);
    
    default Set<Long> findFriendIds(Long userId) {
        return findRelatedUserIdsByStatus(userId, Status.ACCEPTED);
    }
    
    default Set<Long> findPendingRequestUserIds(Long userId) {
        return findRelatedUserIdsByStatus(userId, Status.PENDING);
    }
}

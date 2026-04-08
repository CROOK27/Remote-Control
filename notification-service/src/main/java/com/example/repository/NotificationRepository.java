package com.example.repository;


import com.example.entity.Notification;
import com.example.entity.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Notification> findByUserIdAndStatusOrderByCreatedAtDesc(Long userId, NotificationStatus status);

    long countByUserIdAndStatus(Long userId, NotificationStatus status);

    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.status = :status, n.readAt = CURRENT_TIMESTAMP WHERE n.userId = :userId AND n.id = :notificationId")
    void markAsRead(Long userId, Long notificationId, NotificationStatus status);

    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.status = :status, n.readAt = CURRENT_TIMESTAMP WHERE n.userId = :userId AND n.status = 'UNREAD'")
    void markAllAsRead(Long userId, NotificationStatus status);
}
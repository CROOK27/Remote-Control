package com.example.service;


import com.example.dto.NotificationDto;
import com.example.dto.SendNotificationRequest;
import com.example.dto.UnreadCountDto;
import com.example.entity.Notification;
import com.example.entity.NotificationStatus;
import com.example.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public NotificationDto createNotification(SendNotificationRequest request) {
        Notification notification = new Notification();
        notification.setUserId(request.getUserId());
        notification.setType(request.getType());
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setLink(request.getLink());

        notification = notificationRepository.save(notification);
        log.info("Notification created for user {}: {}", request.getUserId(), notification.getTitle());

        return convertToDto(notification);
    }

    public List<NotificationDto> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<NotificationDto> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, NotificationStatus.UNREAD).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UnreadCountDto getUnreadCount(Long userId) {
        long count = notificationRepository.countByUserIdAndStatus(userId, NotificationStatus.UNREAD);
        return new UnreadCountDto(userId, count);
    }

    @Transactional
    public void markAsRead(Long userId, Long notificationId) {
        notificationRepository.markAsRead(userId, notificationId, NotificationStatus.READ);
        log.info("Notification {} marked as read for user {}", notificationId, userId);
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        notificationRepository.markAllAsRead(userId, NotificationStatus.READ);
        log.info("All notifications marked as read for user {}", userId);
    }

    @Transactional
    public void deleteNotification(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        if (!notification.getUserId().equals(userId)) {
            throw new RuntimeException("Notification does not belong to user");
        }

        notificationRepository.delete(notification);
        log.info("Notification {} deleted for user {}", notificationId, userId);
    }

    private NotificationDto convertToDto(Notification notification) {
        return new NotificationDto(
                notification.getId(),
                notification.getUserId(),
                notification.getType(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getLink(),
                notification.getStatus().name(),
                notification.getCreatedAt(),
                notification.getReadAt()
        );
    }
}

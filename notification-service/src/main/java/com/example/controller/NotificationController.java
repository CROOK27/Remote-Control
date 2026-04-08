package com.example.controller;

import com.example.dto.NotificationDto;
import com.example.dto.SendNotificationRequest;
import com.example.dto.UnreadCountDto;
import com.example.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notifications", description = "User notifications management")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    @Operation(summary = "Create a new notification")
    public ResponseEntity<NotificationDto> createNotification(@Valid @RequestBody SendNotificationRequest request) {
        return ResponseEntity.ok(notificationService.createNotification(request));
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Get all notifications for a user")
    public ResponseEntity<List<NotificationDto>> getUserNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    @GetMapping("/users/{userId}/unread")
    @Operation(summary = "Get unread notifications for a user")
    public ResponseEntity<List<NotificationDto>> getUnreadNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(userId));
    }

    @GetMapping("/users/{userId}/unread/count")
    @Operation(summary = "Get unread count for a user")
    public ResponseEntity<UnreadCountDto> getUnreadCount(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUnreadCount(userId));
    }

    @PutMapping("/users/{userId}/notifications/{notificationId}/read")
    @Operation(summary = "Mark a notification as read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long userId, @PathVariable Long notificationId) {
        notificationService.markAsRead(userId, notificationId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{userId}/notifications/read-all")
    @Operation(summary = "Mark all notifications as read")
    public ResponseEntity<Void> markAllAsRead(@PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{userId}/notifications/{notificationId}")
    @Operation(summary = "Delete a notification")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long userId, @PathVariable Long notificationId) {
        notificationService.deleteNotification(userId, notificationId);
        return ResponseEntity.noContent().build();
    }
}
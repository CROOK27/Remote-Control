package com.example.controller;

import com.example.dto.InvitationCodeResponse;
import com.example.entity.User;
import com.example.service.InvitationService;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;
    private final UserService userService;

    @PostMapping("/generate")
    @Operation(summary = "Generate a 6-digit invitation code (TEACHER only)")
    public ResponseEntity<InvitationCodeResponse> generateCode(
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        if (!"TEACHER".equals(userRole)) {
            return ResponseEntity.status(403).build();
        }
        if (userId == null) {
            return ResponseEntity.status(400).build();
        }
        Long teacherId = Long.parseLong(userId);
        User teacher = userService.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!teacher.getRole().equals(com.example.entity.Role.TEACHER)) {
            return ResponseEntity.status(403).build();
        }
        InvitationCodeResponse response = invitationService.generateCode(teacherId);
        return ResponseEntity.ok(response);
    }
}
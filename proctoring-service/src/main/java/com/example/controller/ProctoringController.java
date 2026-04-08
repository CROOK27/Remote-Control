package com.example.controller;

import com.example.dto.ProctoringEventDto;
import com.example.dto.ProctoringRequest;
import com.example.dto.SuspiciousActionDto;
import com.example.service.ProctoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proctoring")
@Tag(name = "Proctoring", description = "Proctoring events management")
@RequiredArgsConstructor
public class ProctoringController {

    private final ProctoringService proctoringService;

    @PostMapping("/events")
    @Operation(summary = "Log a proctoring event")
    public ResponseEntity<ProctoringEventDto> logEvent(@Valid @RequestBody ProctoringRequest request) {
        return ResponseEntity.ok(proctoringService.logEvent(request));
    }

    @GetMapping("/sessions/{sessionId}/events")
    @Operation(summary = "Get all events for a session")
    public ResponseEntity<List<ProctoringEventDto>> getSessionEvents(@PathVariable Long sessionId) {
        return ResponseEntity.ok(proctoringService.getSessionEvents(sessionId));
    }

    @GetMapping("/users/{userId}/events")
    @Operation(summary = "Get all events for a user")
    public ResponseEntity<List<ProctoringEventDto>> getUserEvents(@PathVariable Long userId) {
        return ResponseEntity.ok(proctoringService.getUserEvents(userId));
    }

    @GetMapping("/sessions/{sessionId}/suspicious")
    @Operation(summary = "Get suspicious actions for a session")
    public ResponseEntity<List<SuspiciousActionDto>> getSuspiciousActions(@PathVariable Long sessionId) {
        return ResponseEntity.ok(proctoringService.getSuspiciousActions(sessionId));
    }

    @PutMapping("/suspicious/{actionId}/resolve")
    @Operation(summary = "Mark suspicious action as resolved")
    public ResponseEntity<Void> resolveSuspiciousAction(@PathVariable Long actionId) {
        proctoringService.resolveSuspiciousAction(actionId);
        return ResponseEntity.ok().build();
    }
}
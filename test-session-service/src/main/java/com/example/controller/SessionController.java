package com.example.controller;


import com.example.dto.*;
import com.example.service.AnswerService;
import com.example.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@Tag(name = "Test Sessions", description = "Test session management")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;
    private final AnswerService answerService;

    @PostMapping("/start")
    @Operation(summary = "Start a new test session")
    public ResponseEntity<SessionDto> startSession(@Valid @RequestBody StartSessionRequest request) {
        return ResponseEntity.ok(sessionService.startSession(request));
    }

    @PostMapping("/{sessionId}/complete")
    @Operation(summary = "Complete a test session")
    public ResponseEntity<SessionDto> completeSession(@PathVariable Long sessionId) {
        return ResponseEntity.ok(sessionService.completeSession(sessionId));
    }

    @GetMapping("/{sessionId}")
    @Operation(summary = "Get session by ID")
    public ResponseEntity<SessionDto> getSession(@PathVariable Long sessionId) {
        return ResponseEntity.ok(sessionService.getSessionById(sessionId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all sessions for a user")
    public ResponseEntity<List<SessionDto>> getUserSessions(@PathVariable Long userId) {
        return ResponseEntity.ok(sessionService.getSessionsByUser(userId));
    }

    @GetMapping("/user/{userId}/active")
    @Operation(summary = "Get active sessions for a user")
    public ResponseEntity<List<SessionDto>> getActiveSessions(@PathVariable Long userId) {
        return ResponseEntity.ok(sessionService.getActiveSessionsByUser(userId));
    }

    @GetMapping("/{sessionId}/results")
    @Operation(summary = "Get session results")
    public ResponseEntity<SessionResultDto> getSessionResults(@PathVariable Long sessionId) {
        return ResponseEntity.ok(sessionService.getSessionResults(sessionId));
    }

    @PostMapping("/{sessionId}/answers")
    @Operation(summary = "Save an answer for a session")
    public ResponseEntity<AnswerDto> saveAnswer(
            @PathVariable Long sessionId,
            @Valid @RequestBody AnswerRequest request) {
        return ResponseEntity.ok(answerService.saveAnswer(sessionId, request));
    }

    @GetMapping("/{sessionId}/answers")
    @Operation(summary = "Get all answers for a session")
    public ResponseEntity<List<AnswerDto>> getSessionAnswers(@PathVariable Long sessionId) {
        return ResponseEntity.ok(answerService.getSessionAnswers(sessionId));
    }
}

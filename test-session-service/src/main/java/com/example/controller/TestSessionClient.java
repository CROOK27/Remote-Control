package com.example.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "test-session-service")
public interface TestSessionClient {

    @GetMapping("/api/sessions/{sessionId}")
    Object getSession(@PathVariable("sessionId") Long sessionId);

    @GetMapping("/api/sessions/{sessionId}/answers")
    Object getSessionAnswers(@PathVariable("sessionId") Long sessionId);
}
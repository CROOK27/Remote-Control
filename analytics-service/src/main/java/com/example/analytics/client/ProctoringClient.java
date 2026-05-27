package com.example.analytics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "proctoring-service", url = "${services.proctoring.url:http://localhost:8084}")
public interface ProctoringClient {
    @GetMapping("/api/proctoring/sessions/{sessionId}")
    Object getProctoringData(@PathVariable("sessionId") Long sessionId);
}

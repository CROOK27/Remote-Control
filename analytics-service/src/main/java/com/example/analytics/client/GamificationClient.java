package com.example.analytics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gamification-service", url = "${services.gamification.url:http://localhost:8085}")
public interface GamificationClient {
    @GetMapping("/api/gamification/users/{userId}/points")
    Object getUserGamification(@PathVariable("userId") Long userId);
}

package com.example.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gamification-service")
public interface GamificationClient {

    @GetMapping("/api/gamification/users/{userId}/points")
    Object getUserGamification(@PathVariable("userId") Long userId);
}

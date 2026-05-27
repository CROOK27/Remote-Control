package com.example.analytics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service", url = "${services.auth.url:http://localhost:8081}")
public interface AuthClient {
    @GetMapping("/api/users/{userId}")
    Object getUser(@PathVariable("userId") Long userId);

    @GetMapping("/api/groups/{groupId}")
    Object getGroup(@PathVariable("groupId") Long groupId);
}

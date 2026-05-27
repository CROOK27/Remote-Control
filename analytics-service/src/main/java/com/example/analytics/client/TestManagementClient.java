package com.example.analytics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "test-management-service", url = "${services.test-management.url:http://localhost:8082}")
public interface TestManagementClient {
    @GetMapping("/api/tests/{testId}")
    Object getTest(@PathVariable("testId") Long testId);

    @GetMapping("/api/tests/{testId}/questions")
    Object getTestQuestions(@PathVariable("testId") Long testId);
}

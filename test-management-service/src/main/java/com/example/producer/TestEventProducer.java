package com.example.producer;


import com.example.event.TestPublishedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendTestPublishedEvent(Long testId, Long teacherId) {
        TestPublishedEvent event = new TestPublishedEvent(testId, teacherId, java.time.LocalDateTime.now());
        kafkaTemplate.send("test-events", event);
        log.info("Sent TestPublishedEvent for test: {}", testId);
    }
}

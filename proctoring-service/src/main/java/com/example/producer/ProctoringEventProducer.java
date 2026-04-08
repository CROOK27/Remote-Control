package com.example.producer;

import com.example.event.SuspiciousActionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProctoringEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendSuspiciousActionEvent(SuspiciousActionEvent event) {
        kafkaTemplate.send("proctoring-events", event);
        log.info("Sent SuspiciousActionEvent to Kafka: {}", event);
    }
}

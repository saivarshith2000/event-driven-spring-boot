package com.varshith.evstarter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class Consumer {
    @KafkaListener(topics = "test-topic", groupId = "group-id")
    public void listenTestTopic(String message) {
        log.info("Received message on 'test-topic' - {}", message);
    }
}

package com.varshith.evstarter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
@EnableScheduling
public class Producer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private int msgCount = 0;

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void sendMessage() {
        String topicName = "test-topic";
        String msg = String.format("Test Message - %d", msgCount);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, msg);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[{}] with offset=[{}]", msg, result.getRecordMetadata().offset());
                msgCount += 1;
            } else {
                log.info("Failed to send message=[{}] due to : {}", msg, ex.getMessage());
            }
        });
    }
}

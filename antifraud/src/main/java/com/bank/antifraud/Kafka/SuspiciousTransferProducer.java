package com.bank.antifraud.Kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuspiciousTransferProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendCreate(String message) {
        kafkaTemplate.send("suspicious-transfers.create", message);
    }

    public void sendUpdate(String payload) {
        kafkaTemplate.send("suspicious-transfers.update", payload);
    }

    public void sendDelete(String payload) {
        kafkaTemplate.send("suspicious-transfers.delete", payload);
    }

    public void getDelete(String payload) {
        kafkaTemplate.send("suspicious-transfers.get", payload);
    }
}

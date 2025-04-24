package com.bank.antifraud.Kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SuspiciousTransferConsumer {

    @KafkaListener(topics = "antifraud_kafka_transfer", groupId = "suspicious_transfer")
    public void listener(ConsumerRecord<String, String> record) {
        System.out.println("получено значение " + record.value());
    }
}

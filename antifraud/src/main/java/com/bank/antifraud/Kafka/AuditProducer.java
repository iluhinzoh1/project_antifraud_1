package com.bank.antifraud.Kafka;

import com.bank.antifraud.DTO.AuditDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditProducer.class);
    private final KafkaTemplate<String, AuditDto> kafkaTemplate;

    public void sendAuditEvent(AuditDto auditDto) {
        kafkaTemplate.send("audit-events", auditDto);
        LOGGER.info("Sent AuditDto to Kafka: {}", auditDto);
    }
}

package com.bank.antifraud.Kafka;

import com.bank.antifraud.DTO.AuditDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Класс для отправки подозрительных
 * данных и аудирования их.
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditProducer {
    private final KafkaTemplate<String, AuditDto> kafkaTemplate;

    public void sendAuditEvent(AuditDto auditDto) {
        kafkaTemplate.send("audit-events", auditDto);
        log.info("Sent AuditDto to AuditProducer: {}", auditDto);
    }
}

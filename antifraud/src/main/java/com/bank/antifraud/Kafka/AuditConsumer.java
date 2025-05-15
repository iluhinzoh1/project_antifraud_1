package com.bank.antifraud.Kafka;

import com.bank.antifraud.DTO.AuditDto;
import com.bank.antifraud.Entities.Audit;
import com.bank.antifraud.Repositories.AuditRepository;
import com.bank.antifraud.mapper.AuditMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Класс для получения подозрительных
 * данных из AuditProducer и сохранения их в базе данных.
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditConsumer {
    private final AuditRepository repository;
    private final AuditMapper mapper;

    @KafkaListener(topics = "audit-events", groupId = "anti-fraud-group")
    public void consumeAuditEvent(ConsumerRecord<String, AuditDto> auditDto) {
        final Audit audit = mapper.toEntityAudit(auditDto.value());
        repository.save(audit);
        log.info("Get AuditDto to AuditConsumer: {}", auditDto);

    }
}

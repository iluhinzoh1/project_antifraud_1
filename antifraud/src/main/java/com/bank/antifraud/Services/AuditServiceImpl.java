package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.AuditDto;
import com.bank.antifraud.Entities.Audit;
import com.bank.antifraud.Kafka.AuditProducer;
import com.bank.antifraud.Repositories.AuditRepository;
import com.bank.antifraud.mapper.AuditMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с подозрительными переводами.
 */
@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuditServiceImpl.class);
    private final AuditRepository auditRepo;
    private final AuditProducer auditProducer;
    private final AuditMapper auditMapper;

    @Override
    @Transactional
    public void logAudit(AuditDto auditDto) {
        final Audit entity = auditMapper.toEntityAudit(auditDto);
        auditRepo.save(entity);
        auditProducer.sendAuditEvent(auditDto);
        LOGGER.info("Audit logged: {}", auditDto);
    }

    @Override
    public List<AuditDto> getAllAuditLogs() {
        final List<Audit> audits = auditRepo.findAll();
        return audits.stream()
                .map(auditMapper::toDtoAudit)
                .collect(Collectors.toList());
    }
}

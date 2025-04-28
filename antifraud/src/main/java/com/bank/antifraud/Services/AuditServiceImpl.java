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
    private final SuspiciousCardTransferServiceImpl  cardService;
    private final SuspiciousPhoneTransferServiceImpl phoneService;
    private final SuspiciousAccountTransferServiceImpl accountService;
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
    @Transactional(readOnly = true)
    public List<AuditDto> getAllAuditLogs() {
        final List<Audit> audits = auditRepo.findAll();
        return audits.stream()
                .map(auditMapper::toDtoAudit)
                .collect(Collectors.toList());
    }

    @Override
    public Object findDtoById(String entityType, Long id) {
        return switch (entityType) {
            case "SuspiciousCardTransfer"    -> cardService.getTransferById(id);
            case "SuspiciousPhoneTransfer"   -> phoneService.getTransferById(id);
            case "SuspiciousAccountTransfer" -> accountService.getTransferById(id);
            default -> throw new IllegalArgumentException("Unknown entity type: " + entityType);
        };
    }

    @Override
    public AuditDto findLastAudit(String entityType, Long entityId) {
        final Audit last = auditRepo
                .findFirstByEntityTypeAndIdOrderByCreatedAtDesc(entityType, entityId);
        return auditMapper.toDtoAudit(last);
    }
}

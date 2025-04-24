package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.AuditDto;
import com.bank.antifraud.Entities.Audit;
import com.bank.antifraud.Repositories.AuditRepository;
import com.bank.antifraud.mapper.AuditMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для работы с подозрительными переводами.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuditServiceImpl.class);
    private final AuditMapper mapper;
    private final AuditRepository auditRepository;


    @Override
    public void logAudit(AuditDto auditDto) {
        if (auditDto == null) {
            throw new IllegalArgumentException("Audit cannot be null");
        }
        final Audit audit = mapper.toEntityAudit(auditDto);
        auditRepository.save(audit);
        LOGGER.info("audit logged :{}", audit);
    }
}

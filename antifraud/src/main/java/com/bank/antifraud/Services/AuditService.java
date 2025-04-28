package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.AuditDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuditService {
    void logAudit(AuditDto auditDto);
    List<AuditDto> getAllAuditLogs();
    Object findDtoById(String entityType, Long id);
    AuditDto findLastAudit(String entityType, Long entityId);
}

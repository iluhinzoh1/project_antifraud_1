package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.AuditDto;
import org.springframework.stereotype.Service;

@Service
public interface AuditService {
    void logAudit(AuditDto auditDto);
}

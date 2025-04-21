package com.bank.antifraud.mapper;

import com.bank.antifraud.DTO.AuditDto;
import com.bank.antifraud.Entities.Audit;
import org.mapstruct.Mapper;
/**
 * Преобразует сущности аудита в DTO и обратно
 */
@Mapper(componentModel = "spring")
public interface AuditMapper {
    AuditDto toDtoAudit(Audit audit);
    Audit toEntityAudit(AuditDto auditDto);
}

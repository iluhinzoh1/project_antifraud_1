package com.bank.antifraud.mapper;

import com.bank.antifraud.DTO.AuditDto;
import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
import com.bank.antifraud.Entities.Audit;
import com.bank.antifraud.Entities.SuspiciousCardTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Преобразует сущности аудита в DTO и обратно
 */
@Mapper(componentModel = "spring")
public interface AuditMapper {
    AuditDto toDtoAudit(Audit audit);
    Audit toEntityAudit(AuditDto auditDto);
    void updateCardTransferFromDTO(AuditDto dto, @MappingTarget Audit transfer);
}

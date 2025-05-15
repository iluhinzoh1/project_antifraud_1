package com.bank.antifraud.mapper;

import com.bank.antifraud.DTO.AuditDto;
import com.bank.antifraud.Entities.Audit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Преобразует сущности аудита в DTO и обратно
 */
@Mapper(componentModel = "spring")
public interface AuditMapper {
    AuditDto toDtoAudit(Audit audit);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Audit toEntityAudit(AuditDto auditDto);
}

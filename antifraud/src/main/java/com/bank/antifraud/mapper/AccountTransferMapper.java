package com.bank.antifraud.mapper;

import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import com.bank.antifraud.Entities.SuspiciousAccountTransfer;
import com.bank.antifraud.Entities.TransferChecked;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountTransferMapper extends AbstractMapper<SuspiciousAccountTransferDto, SuspiciousAccountTransfer> {
    @Override
    @Mapping(source = "id", target = "id")
    SuspiciousAccountTransferDto toDto(SuspiciousAccountTransfer entity);

    @Override
    @Mapping(target = "id", ignore = true)
    SuspiciousAccountTransfer toEntity(SuspiciousAccountTransferDto dto);

    @Override
    @Mapping(target = "id", ignore = true)
    void updateFromDto(SuspiciousAccountTransferDto dto, @MappingTarget SuspiciousAccountTransfer entity);

    @Override
    SuspiciousAccountTransferDto eventToDto(TransferChecked event);
}

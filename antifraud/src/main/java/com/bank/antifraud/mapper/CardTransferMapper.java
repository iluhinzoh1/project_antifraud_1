package com.bank.antifraud.mapper;

import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
import com.bank.antifraud.Entities.SuspiciousCardTransfer;
import com.bank.antifraud.Entities.TransferChecked;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardTransferMapper extends AbstractMapper<SuspiciousCardTransferDto, SuspiciousCardTransfer> {
    @Mapping(source = "id", target = "id")
    SuspiciousCardTransferDto toDto(SuspiciousCardTransfer entity);

    @Mapping(target = "id", ignore = true)
    SuspiciousCardTransfer toEntity(SuspiciousCardTransferDto dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(SuspiciousCardTransferDto dto, @MappingTarget SuspiciousCardTransfer entity);

    SuspiciousCardTransferDto eventToDto(TransferChecked event);
}


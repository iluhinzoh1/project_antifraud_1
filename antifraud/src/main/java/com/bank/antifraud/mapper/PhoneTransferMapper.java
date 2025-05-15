package com.bank.antifraud.mapper;

import com.bank.antifraud.DTO.SuspiciousPhoneTransferDto;
import com.bank.antifraud.Entities.SuspiciousPhoneTransfer;
import com.bank.antifraud.Entities.TransferChecked;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhoneTransferMapper extends AbstractMapper<SuspiciousPhoneTransferDto, SuspiciousPhoneTransfer> {
    @Mapping(source = "id", target = "id")
    SuspiciousPhoneTransferDto toDto(SuspiciousPhoneTransfer entity);

    @Mapping(target = "id", ignore = true)
    SuspiciousPhoneTransfer toEntity(SuspiciousPhoneTransferDto dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(SuspiciousPhoneTransferDto dto, @MappingTarget SuspiciousPhoneTransfer entity);

    SuspiciousPhoneTransferDto eventToDto(TransferChecked event);
}

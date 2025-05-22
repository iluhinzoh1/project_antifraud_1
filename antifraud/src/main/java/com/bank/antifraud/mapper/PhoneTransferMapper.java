package com.bank.antifraud.mapper;

import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
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

    default SuspiciousPhoneTransferDto eventToDto(TransferChecked event) {
        SuspiciousPhoneTransferDto dto = new SuspiciousPhoneTransferDto();
        dto.setPhoneTransferId(event.getAccountDetailsId());
        dto.setIsBlocked(true);
        dto.setIsSuspicious(true);
        dto.setBlockedReason("превышен лимит в 100_000");
        dto.setSuspiciousReason("обнаружено подозрительное поведение"); // или логика
        return dto;
    }

    default SuspiciousPhoneTransferDto updateToDto(TransferChecked event) {
        SuspiciousPhoneTransferDto dto = new SuspiciousPhoneTransferDto();
        dto.setPhoneTransferId(event.getAccountDetailsId());
        dto.setIsBlocked(false);
        dto.setIsSuspicious(false);
        dto.setBlockedReason("превышения лимита не обнаружено");
        dto.setSuspiciousReason("подозрительное поведение не обнаружено");
        return dto;
    }
}

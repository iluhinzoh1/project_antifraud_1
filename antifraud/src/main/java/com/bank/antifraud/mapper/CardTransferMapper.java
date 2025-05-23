package com.bank.antifraud.mapper;

import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
import com.bank.antifraud.Entities.SuspiciousCardTransfer;
import com.bank.antifraud.Entities.TransferChecked;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/** Маппер для преобразования в dto или entity,
 * а так же для преобразования поступающих топиков в подозрительные либо чистые запросы */


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardTransferMapper extends AbstractMapper<SuspiciousCardTransferDto, SuspiciousCardTransfer> {
    @Mapping(source = "id", target = "id")
    SuspiciousCardTransferDto toDto(SuspiciousCardTransfer entity);

    @Mapping(target = "id", ignore = true)
    SuspiciousCardTransfer toEntity(SuspiciousCardTransferDto dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(SuspiciousCardTransferDto dto, @MappingTarget SuspiciousCardTransfer entity);

    default SuspiciousCardTransferDto eventToDto(TransferChecked event) {
        final SuspiciousCardTransferDto dto = new SuspiciousCardTransferDto();
        dto.setCardTransferId(event.getAccountDetailsId());
        dto.setIsBlocked(true);
        dto.setIsSuspicious(true);
        dto.setBlockedReason("превышен лимит в 100_000");
        dto.setSuspiciousReason("обнаружено подозрительное поведение"); // или логика
        return dto;
    }

    default SuspiciousCardTransferDto updateToDto(TransferChecked event) {
        final SuspiciousCardTransferDto dto = new SuspiciousCardTransferDto();
        dto.setCardTransferId(event.getAccountDetailsId());
        dto.setIsBlocked(false);
        dto.setIsSuspicious(false);
        dto.setBlockedReason("превышения лимита не обнаружено");
        dto.setSuspiciousReason("подозрительное поведение не обнаружено");
        return dto;
    }

    default SuspiciousCardTransferDto eventCleanToDto(TransferChecked event) {
        final SuspiciousCardTransferDto dto = new SuspiciousCardTransferDto();
        dto.setCardTransferId(event.getAccountDetailsId());
        dto.setIsBlocked(false);
        dto.setIsSuspicious(false);
        dto.setBlockedReason("превышения лимита не обнаружено");
        dto.setSuspiciousReason("подозрительное поведение не обнаружено");
        return dto;
    }
}


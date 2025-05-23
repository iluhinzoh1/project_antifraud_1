package com.bank.antifraud.mapper;

import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import com.bank.antifraud.Entities.SuspiciousAccountTransfer;
import com.bank.antifraud.Entities.TransferChecked;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/** Маппер для преобразования в dto или entity,
 * а так же для преобразования поступающих топиков в подозрительные либо чистые запросы */


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

    default SuspiciousAccountTransferDto eventToDto(TransferChecked event) {
        final SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();
        dto.setAccountTransferId(event.getAccountDetailsId());
        dto.setIsBlocked(true);
        dto.setIsSuspicious(true);
        dto.setBlockedReason("превышен лимит в 100_000");
        dto.setSuspiciousReason("обнаружено подозрительное поведение"); // или логика
        return dto;
    }

    default SuspiciousAccountTransferDto updateToDto(TransferChecked event) {
        final SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();
        dto.setAccountTransferId(event.getAccountDetailsId());
        dto.setIsBlocked(false);
        dto.setIsSuspicious(false);
        dto.setBlockedReason("превышения лимита не обнаружено");
        dto.setSuspiciousReason("подозрительное поведение не обнаружено");
        return dto;
    }

    default SuspiciousAccountTransferDto eventCleanToDto(TransferChecked event) {
        final SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();
        dto.setAccountTransferId(event.getAccountDetailsId());
        dto.setIsBlocked(false);
        dto.setIsSuspicious(false);
        dto.setBlockedReason("превышения лимита не обнаружено");
        dto.setSuspiciousReason("подозрительное поведение не обнаружено");
        return dto;
    }
}

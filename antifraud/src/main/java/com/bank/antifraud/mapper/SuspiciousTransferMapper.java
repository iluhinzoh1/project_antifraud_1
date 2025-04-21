package com.bank.antifraud.mapper;

import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
import com.bank.antifraud.DTO.SuspiciousPhoneTransferDto;
import com.bank.antifraud.Entities.SuspiciousAccountTransfer;
import com.bank.antifraud.Entities.SuspiciousCardTransfer;
import com.bank.antifraud.Entities.SuspiciousPhoneTransfer;
import org.mapstruct.Mapper;
/**
 * Преобразует сущности подозрительных переводов в DTO и обратно.
 */
@Mapper(componentModel = "spring")
public interface SuspiciousTransferMapper {
    SuspiciousAccountTransferDto toDtoAccountTransfer(SuspiciousAccountTransfer transfer);
    SuspiciousAccountTransfer toEntityAccountTransfer(SuspiciousAccountTransferDto transferDto);

    SuspiciousPhoneTransferDto toDtoPhoneTransfer(SuspiciousPhoneTransfer transfer);
    SuspiciousPhoneTransfer toEntityPhoneTransfer(SuspiciousPhoneTransferDto transferDto);

    SuspiciousCardTransferDto toDtoCardTransfer(SuspiciousCardTransfer transfer);
    SuspiciousCardTransfer toEntityCardTransfer(SuspiciousCardTransferDto transferDto);
}

package com.bank.antifraud.DTO;

import com.bank.antifraud.Entities.TransferChecked;
import com.bank.antifraud.mapper.AccountTransferMapper;
import com.bank.antifraud.mapper.CardTransferMapper;
import com.bank.antifraud.mapper.PhoneTransferMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Класс для преобразования в dto по типу получаемой информации
 */

@Component
@RequiredArgsConstructor
public class SuspiciousDtoFactory {

    private final AccountTransferMapper accountMapper;
    private final CardTransferMapper cardMapper;
    private final PhoneTransferMapper phoneMapper;

    public Object createDto(TransferChecked transfer, String transferType) {
        return switch (transferType) {
            case "ACCOUNT" -> accountMapper.eventToDto(transfer);
            case "CARD" -> cardMapper.eventToDto(transfer);
            case "PHONE" -> phoneMapper.eventToDto(transfer);
            default -> throw new IllegalArgumentException("Unknown transfer type: " + transferType);
        };
    }

    public Object updateDto(TransferChecked transfer, String transferType) {
        return switch (transferType) {
            case "ACCOUNT" -> accountMapper.updateToDto(transfer);
            case "CARD" -> cardMapper.updateToDto(transfer);
            case "PHONE" -> phoneMapper.updateToDto(transfer);
            default -> throw new IllegalArgumentException("Unknown transfer type: " + transferType);
        };
    }
}

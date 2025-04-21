package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
import com.bank.antifraud.Entities.SuspiciousCardTransfer;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Интерфейс для работы с подозрительными переводами.
 */

@Service
public interface SuspiciousCardTransferService {
    SuspiciousCardTransferDto createCardTransfer(SuspiciousCardTransferDto dto); // логируется
    SuspiciousCardTransferDto updateCardTransfer(Long id, SuspiciousCardTransferDto dto); // логируется
    void deleteCardTransfer(Long id); // НЕ логируется
    List<SuspiciousCardTransferDto> getAllCardTransfers(); // НЕ логируется
    SuspiciousCardTransferDto getCardTransferById(Long id);
}

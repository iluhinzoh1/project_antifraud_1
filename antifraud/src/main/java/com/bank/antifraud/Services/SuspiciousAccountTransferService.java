package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SuspiciousAccountTransferService {
    SuspiciousAccountTransferDto createAccountTransfer(SuspiciousAccountTransferDto dto); // логируется
    SuspiciousAccountTransferDto updateAccountTransfer(Long id, SuspiciousAccountTransferDto dto); // логируется
    void deleteAccountTransfer(Long id); // НЕ логируется
    List<SuspiciousAccountTransferDto> getAllAccountTransfers(); // НЕ логируется
    SuspiciousAccountTransferDto getAccountTransferById(Long id);

}

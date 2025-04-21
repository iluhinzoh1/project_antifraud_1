package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import com.bank.antifraud.DTO.SuspiciousPhoneTransferDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SuspiciousPhoneTransferService {
    SuspiciousPhoneTransferDto createPhoneTransfer(SuspiciousPhoneTransferDto  dto); // логируется
    SuspiciousPhoneTransferDto  updatePhoneTransfer(Long id, SuspiciousPhoneTransferDto  dto); // логируется
    void deletePhoneTransfer(Long id); // НЕ логируется
    List<SuspiciousPhoneTransferDto > getAllPhoneTransfers(); // НЕ логируется
    SuspiciousPhoneTransferDto  getPhoneTransferById(Long id);

}

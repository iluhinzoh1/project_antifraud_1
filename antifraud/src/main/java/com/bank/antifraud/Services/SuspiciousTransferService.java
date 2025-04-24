package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SuspiciousTransferService<T> {
    T createTransfer(T dto); // логируется

    T updateTransfer(Long id, T dto); // логируется

    void deleteTransfer(Long id); // НЕ логируется

    List<? extends T> getAllTransfers(); // НЕ логируется

    T getTransferById(Long id);

}

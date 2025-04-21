package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import com.bank.antifraud.Entities.SuspiciousAccountTransfer;
import com.bank.antifraud.Entities.SuspiciousCardTransfer;
import com.bank.antifraud.Repositories.SuspiciousAccountTransferRepository;
import com.bank.antifraud.mapper.SuspiciousTransferMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SuspiciousAccountTransferServiceImpl implements SuspiciousAccountTransferService{

    private SuspiciousTransferMapper mapper;
    private SuspiciousAccountTransferRepository accountTransferRepository;

    @Override
    public SuspiciousAccountTransferDto createAccountTransfer(SuspiciousAccountTransferDto dto) {
        SuspiciousAccountTransfer entity = mapper.toEntityAccountTransfer(dto);
        SuspiciousAccountTransfer saveEntity = accountTransferRepository.save(entity);
        return mapper.toDtoAccountTransfer(saveEntity);
    }

    @Override
    public SuspiciousAccountTransferDto updateAccountTransfer(Long id, SuspiciousAccountTransferDto dto) {
        SuspiciousAccountTransfer entity = accountTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("card transfer with id " + id + " not found"));
        mapper.updateAccountTransferFromDTO(dto, entity);

        SuspiciousAccountTransfer saveEntity = accountTransferRepository.save(entity);
        return mapper.toDtoAccountTransfer(saveEntity);
    }

    @Override
    public void deleteAccountTransfer(Long id) {
        SuspiciousAccountTransfer entity = accountTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("card transfer with id " + id + " not found"));
        accountTransferRepository.delete(entity);
    }

    @Override
    public List<SuspiciousAccountTransferDto> getAllAccountTransfers() {
        List<SuspiciousAccountTransfer> transfer = accountTransferRepository.findAll();
        return transfer.stream().map(mapper::toDtoAccountTransfer).collect(Collectors.toList());
    }

    @Override
    public SuspiciousAccountTransferDto getAccountTransferById(Long id) {
        SuspiciousAccountTransfer entity = accountTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found"));
        return mapper.toDtoAccountTransfer(entity);
    }
}

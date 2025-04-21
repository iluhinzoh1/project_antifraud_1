package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
import com.bank.antifraud.DTO.SuspiciousPhoneTransferDto;
import com.bank.antifraud.Entities.SuspiciousCardTransfer;
import com.bank.antifraud.Entities.SuspiciousPhoneTransfer;
import com.bank.antifraud.Repositories.SuspiciousPhoneTransferRepository;
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
public class SuspiciousPhoneTransferServiceImpl implements SuspiciousPhoneTransferService{

    private SuspiciousTransferMapper mapper;
    private SuspiciousPhoneTransferRepository phoneTransferRepository;

    @Override
    public SuspiciousPhoneTransferDto createPhoneTransfer(SuspiciousPhoneTransferDto dto) {
        SuspiciousPhoneTransfer entity = mapper.toEntityPhoneTransfer(dto);
        SuspiciousPhoneTransfer saveEntity = phoneTransferRepository.save(entity);
        return mapper.toDtoPhoneTransfer(saveEntity);
    }

    @Override
    public SuspiciousPhoneTransferDto updatePhoneTransfer(Long id, SuspiciousPhoneTransferDto dto) {
        SuspiciousPhoneTransfer entity = phoneTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("card transfer with id " + id + " not found"));
        mapper.updatePhoneTransferFromDTO(dto, entity);

        SuspiciousPhoneTransfer saveEntity = phoneTransferRepository.save(entity);
        return mapper.toDtoPhoneTransfer(saveEntity);
    }

    @Override
    public void deletePhoneTransfer(Long id) {
        SuspiciousPhoneTransfer entity = phoneTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("card transfer with id " + id + " not found"));
        phoneTransferRepository.delete(entity);
    }

    @Override
    public List<SuspiciousPhoneTransferDto> getAllPhoneTransfers() {
        List<SuspiciousPhoneTransfer> transfer = phoneTransferRepository.findAll();
        return transfer.stream().map(mapper::toDtoPhoneTransfer).collect(Collectors.toList());
    }

    @Override
    public SuspiciousPhoneTransferDto getPhoneTransferById(Long id) {
        SuspiciousPhoneTransfer entity = phoneTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found"));
        return mapper.toDtoPhoneTransfer(entity);
    }
}

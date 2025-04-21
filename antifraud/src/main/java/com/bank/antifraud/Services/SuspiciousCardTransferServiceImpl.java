package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
import com.bank.antifraud.Entities.SuspiciousCardTransfer;
import com.bank.antifraud.Repositories.SuspiciousCardTransferRepository;
import com.bank.antifraud.mapper.SuspiciousTransferMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с подозрительными переводами.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SuspiciousCardTransferServiceImpl implements SuspiciousCardTransferService {

    private SuspiciousTransferMapper mapper;
    private SuspiciousCardTransferRepository cardTransferRepository;

    @Override
    public SuspiciousCardTransferDto createCardTransfer(SuspiciousCardTransferDto dto) {
        SuspiciousCardTransfer entity = mapper.toEntityCardTransfer(dto);
        SuspiciousCardTransfer saveEntity = cardTransferRepository.save(entity);
        return mapper.toDtoCardTransfer(saveEntity);
    }

    @Override
    public SuspiciousCardTransferDto updateCardTransfer(Long id, SuspiciousCardTransferDto dto) {
        SuspiciousCardTransfer entity = cardTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("card transfer with id " + id + " not found"));
        mapper.updateCardTransferFromDTO(dto, entity);

        SuspiciousCardTransfer saveEntity = cardTransferRepository.save(entity);
        return mapper.toDtoCardTransfer(saveEntity);
    }

    @Override
    public void deleteCardTransfer(Long id) {
        SuspiciousCardTransfer entity = cardTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("card transfer with id " + id + " not found"));
        cardTransferRepository.delete(entity);
    }

    @Override
    public List<SuspiciousCardTransferDto> getAllCardTransfers() {
        List<SuspiciousCardTransfer> transfer = cardTransferRepository.findAll();
        return transfer.stream().map(mapper::toDtoCardTransfer).collect(Collectors.toList());
    }

    @Override
    public SuspiciousCardTransferDto getCardTransferById(Long id) {
        SuspiciousCardTransfer entity = cardTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found"));
        return mapper.toDtoCardTransfer(entity);
    }
}

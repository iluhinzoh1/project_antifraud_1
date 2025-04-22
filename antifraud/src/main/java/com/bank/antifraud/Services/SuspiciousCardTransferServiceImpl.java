package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
import com.bank.antifraud.Entities.SuspiciousCardTransfer;
import com.bank.antifraud.Repositories.SuspiciousCardTransferRepository;
import com.bank.antifraud.mapper.SuspiciousTransferMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(SuspiciousCardTransferServiceImpl.class);
    private SuspiciousTransferMapper mapper;
    private SuspiciousCardTransferRepository cardTransferRepository;


    @Override
    public SuspiciousCardTransferDto createCardTransfer(SuspiciousCardTransferDto dto) {
        final SuspiciousCardTransfer entity = mapper.toEntityCardTransfer(dto);
        final SuspiciousCardTransfer saveEntity = cardTransferRepository.save(entity);
        LOGGER.info("account is created {}", saveEntity);
        return mapper.toDtoCardTransfer(saveEntity);
    }

    @Override
    public SuspiciousCardTransferDto updateCardTransfer(Long id, SuspiciousCardTransferDto dto) {
        final SuspiciousCardTransfer entity = cardTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("transfer with id " + id + " not found"));
        mapper.updateCardTransferFromDTO(dto, entity);

        final SuspiciousCardTransfer saveEntity = cardTransferRepository.save(entity);
        LOGGER.info("account is updated {}", saveEntity);
        return mapper.toDtoCardTransfer(saveEntity);
    }

    @Override
    public void deleteCardTransfer(Long id) {
        final SuspiciousCardTransfer entity = cardTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("card transfer with id " + id + " not delete"));
        cardTransferRepository.delete(entity);
        LOGGER.info("account is deleted {}", entity);
    }

    @Override
    public List<SuspiciousCardTransferDto> getAllCardTransfers() {
        final List<SuspiciousCardTransfer> transfer = cardTransferRepository.findAll();
        LOGGER.info("all account information {}", transfer);
        return transfer.stream().map(mapper::toDtoCardTransfer).collect(Collectors.toList());
    }

    @Override
    public SuspiciousCardTransferDto getCardTransferById(Long id) {
        final SuspiciousCardTransfer entity = cardTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found"));
        LOGGER.info("account information {}", entity);
        return mapper.toDtoCardTransfer(entity);
    }
}

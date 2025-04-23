package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.SuspiciousPhoneTransferDto;
import com.bank.antifraud.Entities.SuspiciousPhoneTransfer;
import com.bank.antifraud.Repositories.SuspiciousPhoneTransferRepository;
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
public class SuspiciousPhoneTransferServiceImpl implements SuspiciousPhoneTransferService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuspiciousPhoneTransferServiceImpl.class);
    private final SuspiciousTransferMapper mapper;
    private final SuspiciousPhoneTransferRepository phoneTransferRepository;


    @Override
    public SuspiciousPhoneTransferDto createPhoneTransfer(SuspiciousPhoneTransferDto dto) {
        final SuspiciousPhoneTransfer entity = mapper.toEntityPhoneTransfer(dto);
        final SuspiciousPhoneTransfer saveEntity = phoneTransferRepository.save(entity);
        LOGGER.info("account is created {}", saveEntity);
        return mapper.toDtoPhoneTransfer(saveEntity);
    }

    @Override
    public SuspiciousPhoneTransferDto updatePhoneTransfer(Long id, SuspiciousPhoneTransferDto dto) {
        final SuspiciousPhoneTransfer entity = phoneTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("transfer with id " + id + " not found"));
        mapper.updatePhoneTransferFromDTO(dto, entity);

        final SuspiciousPhoneTransfer saveEntity = phoneTransferRepository.save(entity);
        LOGGER.info("account is updated {}", saveEntity);
        return mapper.toDtoPhoneTransfer(saveEntity);
    }

    @Override
    public void deletePhoneTransfer(Long id) {
        final SuspiciousPhoneTransfer entity = phoneTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("card transfer with id " + id + " not delete"));
        phoneTransferRepository.delete(entity);
        LOGGER.info("account is deleted {}", entity);
    }

    @Override
    public List<SuspiciousPhoneTransferDto> getAllPhoneTransfers() {
        final List<SuspiciousPhoneTransfer> transfer = phoneTransferRepository.findAll();
        LOGGER.info("all account information {}", transfer);
        return transfer.stream().map(mapper::toDtoPhoneTransfer).collect(Collectors.toList());
    }

    @Override
    public SuspiciousPhoneTransferDto getPhoneTransferById(Long id) {
        final SuspiciousPhoneTransfer entity = phoneTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found"));
        LOGGER.info("account information {}", entity);
        return mapper.toDtoPhoneTransfer(entity);
    }
}

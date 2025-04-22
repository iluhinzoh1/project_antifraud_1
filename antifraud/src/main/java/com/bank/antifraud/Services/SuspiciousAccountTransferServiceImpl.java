package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import com.bank.antifraud.Entities.SuspiciousAccountTransfer;
import com.bank.antifraud.Repositories.SuspiciousAccountTransferRepository;
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
public class SuspiciousAccountTransferServiceImpl implements SuspiciousAccountTransferService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SuspiciousAccountTransferServiceImpl.class);
    private SuspiciousTransferMapper mapper;
    private SuspiciousAccountTransferRepository accountTransferRepository;


    @Override
    public SuspiciousAccountTransferDto createAccountTransfer(SuspiciousAccountTransferDto dto) {
        final SuspiciousAccountTransfer entity = mapper.toEntityAccountTransfer(dto);
        final SuspiciousAccountTransfer saveEntity = accountTransferRepository.save(entity);
        LOGGER.info("account is created {}", saveEntity);
        return mapper.toDtoAccountTransfer(saveEntity);
    }

    @Override
    public SuspiciousAccountTransferDto updateAccountTransfer(Long id, SuspiciousAccountTransferDto dto) {
        final SuspiciousAccountTransfer entity = accountTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("transfer with id " +
                        id + " not found"));
        mapper.updateAccountTransferFromDTO(dto, entity);

        final SuspiciousAccountTransfer saveEntity = accountTransferRepository.save(entity);
        LOGGER.info("account is updated {}", saveEntity);
        return mapper.toDtoAccountTransfer(saveEntity);
    }

    @Override
    public void deleteAccountTransfer(Long id) {
        final SuspiciousAccountTransfer entity = accountTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("card transfer with id " + id + " not delete"));
        accountTransferRepository.delete(entity);
        LOGGER.info("account is deleted {}", entity);
    }

    @Override
    public List<SuspiciousAccountTransferDto> getAllAccountTransfers() {
        final List<SuspiciousAccountTransfer> transfer = accountTransferRepository.findAll();
        LOGGER.info("all account information {}", transfer);
        return transfer.stream().map(mapper::toDtoAccountTransfer).collect(Collectors.toList());
    }

    @Override
    public SuspiciousAccountTransferDto getAccountTransferById(Long id) {
        final SuspiciousAccountTransfer entity = accountTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found"));
        LOGGER.info("account information {}", entity);
        return mapper.toDtoAccountTransfer(entity);
    }
}

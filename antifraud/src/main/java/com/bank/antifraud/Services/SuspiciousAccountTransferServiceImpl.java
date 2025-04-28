package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import com.bank.antifraud.Entities.SuspiciousAccountTransfer;
import com.bank.antifraud.Kafka.SuspiciousTransferProducer;
import com.bank.antifraud.Repositories.SuspiciousAccountTransferRepository;
import com.bank.antifraud.mapper.SuspiciousTransferMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Сервис для работы с подозрительными переводами.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SuspiciousAccountTransferServiceImpl implements SuspiciousTransferService<SuspiciousAccountTransferDto> {

    private final SuspiciousTransferMapper mapper;
    private final SuspiciousAccountTransferRepository accountTransferRepository;
    private final SuspiciousTransferProducer producer;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public SuspiciousAccountTransferDto createTransfer(SuspiciousAccountTransferDto dto) {
        final SuspiciousAccountTransfer entity = mapper.toEntityAccountTransfer(dto);
        final SuspiciousAccountTransfer saveEntity = accountTransferRepository.save(entity);
        try {
            final SuspiciousAccountTransferDto result = mapper.toDtoAccountTransfer(saveEntity);
            producer.sendCreate(objectMapper.writeValueAsString(result));
            log.info("account is created {}", saveEntity);
            return mapper.toDtoAccountTransfer(saveEntity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public SuspiciousAccountTransferDto updateTransfer(Long id, SuspiciousAccountTransferDto dto) {
        final SuspiciousAccountTransfer entity = accountTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("account transfer with id " +
                        id + " not found"));
        mapper.updateAccountTransferFromDTO(dto, entity);
        final SuspiciousAccountTransfer saveEntity = accountTransferRepository.save(entity);
        try {
            final SuspiciousAccountTransferDto result = mapper.toDtoAccountTransfer(saveEntity);
            producer.sendUpdate(objectMapper.writeValueAsString(result));
            log.info("account transfer is updated {}", saveEntity);
            return mapper.toDtoAccountTransfer(saveEntity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void deleteTransfer(Long id) {
        final SuspiciousAccountTransfer entity = accountTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("transfer with id " + id + " not delete"));
        accountTransferRepository.delete(entity);
        try {
            final SuspiciousAccountTransferDto result = mapper.toDtoAccountTransfer(entity);
            producer.sendDelete(objectMapper.writeValueAsString(result));
            log.info("account transfer is deleted {}", entity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuspiciousAccountTransferDto> getAllTransfers() {
        final List<SuspiciousAccountTransfer> transfers = accountTransferRepository.findAll();
        final List<SuspiciousAccountTransferDto> dtos = transfers.stream()
                .map(mapper::toDtoAccountTransfer)
                .collect(Collectors.toList());
        log.info("all account transfer DTOs: {}", dtos);
        try {
            final String result = objectMapper.writeValueAsString(dtos);
            producer.sendGet(result);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize DTO list to JSON", e);
            throw new RuntimeException("Kafka send failed", e);
        }
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public SuspiciousAccountTransferDto getTransferById(Long id) {
        final SuspiciousAccountTransfer entity = accountTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found"));
        log.info("account transfer information {}", entity);
        return mapper.toDtoAccountTransfer(entity);
    }
}

package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
import com.bank.antifraud.Entities.SuspiciousCardTransfer;
import com.bank.antifraud.Kafka.SuspiciousTransferProducer;
import com.bank.antifraud.Repositories.SuspiciousCardTransferRepository;
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
public class SuspiciousCardTransferServiceImpl implements SuspiciousTransferService<SuspiciousCardTransferDto> {
    private final SuspiciousTransferMapper mapper;
    private final SuspiciousCardTransferRepository cardTransferRepository;
    private final SuspiciousTransferProducer producer;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public SuspiciousCardTransferDto createTransfer(SuspiciousCardTransferDto dto) {
        final SuspiciousCardTransfer entity = mapper.toEntityCardTransfer(dto);
        final SuspiciousCardTransfer saveEntity = cardTransferRepository.save(entity);
        try {
            final SuspiciousCardTransferDto result = mapper.toDtoCardTransfer(saveEntity);
            producer.sendCreate(objectMapper.writeValueAsString(result));
            log.info("card account is created {}", saveEntity);
            return mapper.toDtoCardTransfer(saveEntity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public SuspiciousCardTransferDto updateTransfer(Long id, SuspiciousCardTransferDto dto) {
        final SuspiciousCardTransfer entity = cardTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("card transfer with id " + id + " not found"));
        mapper.updateCardTransferFromDTO(dto, entity);
        final SuspiciousCardTransfer saveEntity = cardTransferRepository.save(entity);
        try {
            final SuspiciousCardTransferDto result = mapper.toDtoCardTransfer(saveEntity);
            producer.sendUpdate(objectMapper.writeValueAsString(result));
            log.info("card account is updated {}", saveEntity);
            return mapper.toDtoCardTransfer(saveEntity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void deleteTransfer(Long id) {
        final SuspiciousCardTransfer entity = cardTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("transfer with id " + id + " not delete"));
        cardTransferRepository.delete(entity);
        try {
            final SuspiciousCardTransferDto result = mapper.toDtoCardTransfer(entity);
            producer.sendDelete(objectMapper.writeValueAsString(result));
            log.info("card account is deleted {}", entity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuspiciousCardTransferDto> getAllTransfers() {
        final List<SuspiciousCardTransfer> transfers = cardTransferRepository.findAll();
        final List<SuspiciousCardTransferDto> dtos = transfers.stream()
                .map(mapper::toDtoCardTransfer)
                .collect(Collectors.toList());
        log.info("all card transfer DTOs: {}", dtos);
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
    public SuspiciousCardTransferDto getTransferById(Long id) {
        final SuspiciousCardTransfer entity = cardTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found"));
        log.info("card account information {}", entity);
        return mapper.toDtoCardTransfer(entity);
    }
}

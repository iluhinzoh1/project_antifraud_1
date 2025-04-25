package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.SuspiciousPhoneTransferDto;
import com.bank.antifraud.Entities.SuspiciousPhoneTransfer;
import com.bank.antifraud.Kafka.SuspiciousTransferProducer;
import com.bank.antifraud.Repositories.SuspiciousPhoneTransferRepository;
import com.bank.antifraud.mapper.SuspiciousTransferMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequiredArgsConstructor
public class SuspiciousPhoneTransferServiceImpl implements SuspiciousTransferService<SuspiciousPhoneTransferDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SuspiciousPhoneTransferServiceImpl.class);
    private final SuspiciousTransferMapper mapper;
    private final SuspiciousPhoneTransferRepository phoneTransferRepository;
    private final SuspiciousTransferProducer producer;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public SuspiciousPhoneTransferDto createTransfer(SuspiciousPhoneTransferDto dto) {
        final SuspiciousPhoneTransfer entity = mapper.toEntityPhoneTransfer(dto);
        final SuspiciousPhoneTransfer saveEntity = phoneTransferRepository.save(entity);
        try {
            final SuspiciousPhoneTransferDto result = mapper.toDtoPhoneTransfer(saveEntity);
            producer.sendCreate(objectMapper.writeValueAsString(result));
            LOGGER.info("phone account is created {}", saveEntity);
            return mapper.toDtoPhoneTransfer(saveEntity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public SuspiciousPhoneTransferDto updateTransfer(Long id, SuspiciousPhoneTransferDto dto) {
        final SuspiciousPhoneTransfer entity = phoneTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("phone transfer with id " + id + " not found"));
        mapper.updatePhoneTransferFromDTO(dto, entity);
        final SuspiciousPhoneTransfer saveEntity = phoneTransferRepository.save(entity);
        try {
            final SuspiciousPhoneTransferDto result = mapper.toDtoPhoneTransfer(saveEntity);
            producer.sendUpdate(objectMapper.writeValueAsString(result));
            LOGGER.info("phone account is updated {}", saveEntity);
            return mapper.toDtoPhoneTransfer(saveEntity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void deleteTransfer(Long id) {
        final SuspiciousPhoneTransfer entity = phoneTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("transfer with id " + id + " not delete"));
        phoneTransferRepository.delete(entity);
        try {
            final SuspiciousPhoneTransferDto result = mapper.toDtoPhoneTransfer(entity);
            producer.sendDelete(objectMapper.writeValueAsString(result));
            LOGGER.info("phone account is deleted {}", entity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuspiciousPhoneTransferDto> getAllTransfers() {
        final List<SuspiciousPhoneTransfer> transfers = phoneTransferRepository.findAll();
        final List<SuspiciousPhoneTransferDto> dtos = transfers.stream()
                .map(mapper::toDtoPhoneTransfer)
                .collect(Collectors.toList());
        LOGGER.info("all phone transfer DTOs: {}", dtos);
        try {
            final String result = objectMapper.writeValueAsString(dtos);
            producer.sendGet(result);
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to serialize DTO list to JSON", e);
            throw new RuntimeException("Kafka send failed", e);
        }
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public SuspiciousPhoneTransferDto getTransferById(Long id) {
        final SuspiciousPhoneTransfer entity = phoneTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found"));
        LOGGER.info("phone account information {}", entity);
        return mapper.toDtoPhoneTransfer(entity);
    }
}

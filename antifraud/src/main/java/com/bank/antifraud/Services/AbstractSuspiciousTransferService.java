package com.bank.antifraud.Services;

import com.bank.antifraud.Kafka.SuspiciousTransferProducer;
import com.bank.antifraud.mapper.AbstractMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractSuspiciousTransferService<
        D,
        E,
        R extends JpaRepository<E, Long>,
        M extends AbstractMapper<D, E>>
        implements SuspiciousTransferService<D> {

    private static final String ENTITY_NOT_FOUND_MSG = "Entity with id ";
    private static final String NOT_FOUND_SUFFIX = " not found";
    private static final String CREATE_ACTION = "create";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String GET_ACTION = "get";

    protected final R repository;
    protected final M mapper;
    protected final SuspiciousTransferProducer producer;
    protected final ObjectMapper objectMapper;

    @Override
    @Transactional
    public D createTransfer(D dto) {
        final E entity = mapper.toEntity(dto);
        final E savedEntity = repository.save(entity);
        final D result = mapper.toDto(savedEntity);
        sendKafkaMessage(CREATE_ACTION, result);
        return result;
    }

    @Override
    @Transactional
    public D updateTransfer(Long id, D dto) {
        final E entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MSG + id + NOT_FOUND_SUFFIX));
        mapper.updateFromDto(dto, entity);
        final E savedEntity = repository.save(entity);
        final D result = mapper.toDto(savedEntity);
        sendKafkaMessage(UPDATE_ACTION, result);
        return result;
    }

    @Override
    @Transactional
    public void deleteTransfer(Long id) {
        final E entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MSG + id + NOT_FOUND_SUFFIX));
        repository.delete(entity);
        final D result = mapper.toDto(entity);
        sendKafkaMessage(DELETE_ACTION, result);
    }

    @Override
    @Transactional(readOnly = true)
    public List<D> getAllTransfers() {
        final List<E> entities = repository.findAll();
        final List<D> dtos = entities.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        sendKafkaMessage(GET_ACTION, dtos);
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public D getTransferById(Long id) {
        final E entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MSG + id + NOT_FOUND_SUFFIX));
        return mapper.toDto(entity);
    }

    private void sendKafkaMessage(String action, Object data) {
        try {
            final String message = objectMapper.writeValueAsString(data);
            switch (action) {
                case CREATE_ACTION -> producer.sendCreate(message);
                case UPDATE_ACTION -> producer.sendUpdate(message);
                case DELETE_ACTION -> producer.sendDelete(message);
                case GET_ACTION -> producer.sendGet(message);
                default -> log.warn("Unknown action: {}", action);
            }
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize data for Kafka", e);
            throw new RuntimeException("Kafka serialization failed", e);
        }
    }
}

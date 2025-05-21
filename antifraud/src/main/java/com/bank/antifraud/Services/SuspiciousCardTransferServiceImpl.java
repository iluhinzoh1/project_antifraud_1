package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
import com.bank.antifraud.Entities.SuspiciousCardTransfer;
import com.bank.antifraud.Kafka.SuspiciousTransferProducer;
import com.bank.antifraud.Repositories.SuspiciousCardTransferRepository;
import com.bank.antifraud.mapper.CardTransferMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с подозрительными переводами по карте.
 */
@Service
@Slf4j
public class SuspiciousCardTransferServiceImpl extends AbstractSuspiciousTransferService
        <SuspiciousCardTransferDto,
                SuspiciousCardTransfer,
                SuspiciousCardTransferRepository,
                CardTransferMapper> {

    public SuspiciousCardTransferServiceImpl(SuspiciousCardTransferRepository repository,
                                             CardTransferMapper mapper,
                                             SuspiciousTransferProducer producer,
                                             ObjectMapper objectMapper) {
        super(repository, mapper, producer, objectMapper);
    }

    @Override
    public boolean existsByTransferId(Long transferId) {
        return repository.existsByCardTransferId(transferId);
    }
}


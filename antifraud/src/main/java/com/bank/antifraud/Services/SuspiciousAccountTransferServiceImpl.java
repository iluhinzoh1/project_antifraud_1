package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import com.bank.antifraud.Entities.SuspiciousAccountTransfer;
import com.bank.antifraud.Kafka.SuspiciousTransferProducer;
import com.bank.antifraud.Repositories.SuspiciousAccountTransferRepository;
import com.bank.antifraud.mapper.AccountTransferMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * Сервис для работы с подозрительными переводами по аккаунту.
 */
@Service
@Slf4j
public class SuspiciousAccountTransferServiceImpl extends AbstractSuspiciousTransferService
        <SuspiciousAccountTransferDto,
                SuspiciousAccountTransfer,
                SuspiciousAccountTransferRepository,
                AccountTransferMapper> {

    public SuspiciousAccountTransferServiceImpl(SuspiciousAccountTransferRepository repository,
                                                AccountTransferMapper mapper,
                                                SuspiciousTransferProducer producer,
                                                ObjectMapper objectMapper) {
        super(repository, mapper, producer, objectMapper);
    }

    @Override
    public boolean existsByTransferId(Long accountTransferId) {
        return repository.existsByAccountTransferId(accountTransferId);
    }
}

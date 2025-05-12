package com.bank.antifraud.Services;

import com.bank.antifraud.DTO.SuspiciousPhoneTransferDto;
import com.bank.antifraud.Entities.SuspiciousPhoneTransfer;
import com.bank.antifraud.Kafka.SuspiciousTransferProducer;
import com.bank.antifraud.Repositories.SuspiciousPhoneTransferRepository;
import com.bank.antifraud.mapper.PhoneTransferMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * Сервис для работы с подозрительными переводами по телефону.
 */

@Service
@Slf4j
public class SuspiciousPhoneTransferServiceImpl extends AbstractSuspiciousTransferService
        <SuspiciousPhoneTransferDto,
                SuspiciousPhoneTransfer,
                SuspiciousPhoneTransferRepository,
                PhoneTransferMapper> {

    public SuspiciousPhoneTransferServiceImpl(SuspiciousPhoneTransferRepository repository,
                                              PhoneTransferMapper mapper,
                                              SuspiciousTransferProducer producer,
                                              ObjectMapper objectMapper) {
        super(repository, mapper, producer, objectMapper);
    }
}

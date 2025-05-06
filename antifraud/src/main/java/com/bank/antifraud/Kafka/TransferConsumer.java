package com.bank.antifraud.Kafka;

import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
import com.bank.antifraud.DTO.SuspiciousDtoFactory;
import com.bank.antifraud.DTO.SuspiciousPhoneTransferDto;
import com.bank.antifraud.Entities.TransferChecked;
import com.bank.antifraud.Services.SuspiciousAccountTransferServiceImpl;
import com.bank.antifraud.Services.SuspiciousCardTransferServiceImpl;
import com.bank.antifraud.Services.SuspiciousPhoneTransferServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferConsumer {
    private final static BigDecimal MAX_ALLOWED_AMOUNT = new BigDecimal("100000");
    private final TransferProducer producer;
    private final SuspiciousAccountTransferServiceImpl accountService;
    private final SuspiciousCardTransferServiceImpl cardService;
    private final SuspiciousPhoneTransferServiceImpl phoneService;
    private final SuspiciousDtoFactory factory;

    @KafkaListener(topics = "transfer-events", groupId = "anti-fraud-group")
    public void handleTransferEvent(TransferChecked transfer) {
        if (checkSuspicious(transfer.getAmount())) {
            final Object dto = factory.createDto(transfer, transfer.getTransferType());

            switch (transfer.getTransferType()) {
                case "ACCOUNT" -> accountService.createTransfer((SuspiciousAccountTransferDto) dto);
                case "CARD" -> cardService.createTransfer((SuspiciousCardTransferDto) dto);
                case "PHONE" -> phoneService.createTransfer((SuspiciousPhoneTransferDto) dto);
                default -> throw new IllegalStateException("Unexpected value: " + transfer.getTransferType());
            }
            producer.sendVerdict(transfer.getTransferId(),
                    "BLOCKED",
                    "Сумма превышает допустим лимит");
            log.info("Get verdict: {}", transfer);
        }
    }


    public Boolean checkSuspicious(BigDecimal transfer) {
        return transfer.compareTo(MAX_ALLOWED_AMOUNT) > 0;
    }
}



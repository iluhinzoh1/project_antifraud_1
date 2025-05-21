package com.bank.antifraud.Kafka;

import com.bank.antifraud.DTO.*;
import com.bank.antifraud.Entities.TransferChecked;
import com.bank.antifraud.Services.SuspiciousAccountTransferServiceImpl;
import com.bank.antifraud.Services.SuspiciousCardTransferServiceImpl;
import com.bank.antifraud.Services.SuspiciousPhoneTransferServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransferConsumer {

    private static final BigDecimal LIMIT = BigDecimal.valueOf(100_000);
    private final ThreadLocal<TransferChecked> oldChecked = new ThreadLocal<>();
    private final SuspiciousAccountTransferServiceImpl accountService;
    private final SuspiciousCardTransferServiceImpl cardService;
    private final SuspiciousPhoneTransferServiceImpl phoneService;
    private final SuspiciousDtoFactory factory;
    private final TransferProducer verdictProducer;

    @KafkaListener(topics = "transfer.account", groupId = "transfer-group")
    public void listenAccount(@Payload TransferChecked transfer) {
        if (isBlocked(transfer.getAmount()) ) {
            boolean exists = accountService.existsByTransferId(transfer.getAccountDetailsId());
            if (exists) {
                update("ACCOUNT", transfer);
            } else {
                create("ACCOUNT", transfer);
            }
        }
    }

    @KafkaListener(topics = "transfer.card", groupId = "transfer-group")
    public void listenCard(@Payload TransferChecked transfer) {

    }

    @KafkaListener(topics = "transfer.phone", groupId = "transfer-group")
    public void listenPhone(@Payload TransferChecked transfer) {

    }

    private void create(String type, TransferChecked t) {
        Object dto = factory.createDto(t, type);
        switch (type) {
            case "ACCOUNT" -> accountService.createTransfer((SuspiciousAccountTransferDto) dto);
            case "CARD"    -> cardService.createTransfer((SuspiciousCardTransferDto) dto);
            case "PHONE"   -> phoneService.createTransfer((SuspiciousPhoneTransferDto) dto);
            default        -> throw new IllegalArgumentException("Неизвестный тип перевода: " + type);
        }
        log.info("Создана подозрительная транзакция типа {}", type);
    }

    private void update(String type, TransferChecked t) {
        Object dto = factory.createDto(t, type);
        Long id = t.getId();
        switch (type) {
            case "ACCOUNT" -> accountService.updateTransfer(id, (SuspiciousAccountTransferDto) dto);
            case "CARD"    -> cardService.updateTransfer(id, (SuspiciousCardTransferDto) dto);
            case "PHONE"   -> phoneService.updateTransfer(id, (SuspiciousPhoneTransferDto) dto);
            default        -> throw new IllegalArgumentException("Неизвестный тип перевода: " + type);
        }
        log.info("Обновлена подозрительная транзакция типа {}", type);
    }
    private boolean isBlocked(BigDecimal amount) {
        return amount.compareTo(LIMIT) > 0;
    }
}



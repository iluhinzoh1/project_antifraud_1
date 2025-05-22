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
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransferConsumer {

    private static final BigDecimal LIMIT = BigDecimal.valueOf(100_000);
    private final SuspiciousAccountTransferServiceImpl accountService;
    private final SuspiciousCardTransferServiceImpl cardService;
    private final SuspiciousPhoneTransferServiceImpl phoneService;
    private final SuspiciousDtoFactory factory;
    private final TransferProducer verdictProducer;
    private static final String ACCOUNT = "ACCOUNT";
    private static final String CARD = "CARD";
    private static final String PHONE = "PHONE";
    private static final String BLOCKED = "BLOCKED";
    private static final String ALLOWED = "ALLOWED";

    @KafkaListener(topics = "transfer.account", groupId = "transfer-group")
    public void listenAccount(@Payload TransferChecked transfer) {
        if (isBlocked(transfer.getAmount())) {
            boolean exists = accountService.existsByTransferId(transfer.getAccountDetailsId());
            if (exists) {
                update(ACCOUNT, transfer, transfer.getId());
                verdictProducer.sendVerdict(transfer.getId(), BLOCKED, transfer.getPurpose());
            } else {
                create(ACCOUNT, transfer);
                verdictProducer.sendVerdict(transfer.getId(), BLOCKED, transfer.getPurpose());
            }
        } else if (!isBlocked(transfer.getAmount())
                && accountService.existsByTransferId(transfer.getAccountDetailsId())) {
            cleanUpdate(ACCOUNT, transfer, transfer.getId());
            verdictProducer.sendVerdict(transfer.getId(), ALLOWED, transfer.getPurpose());
        }
    }

    @KafkaListener(topics = "transfer.card", groupId = "transfer-group")
    public void listenCard(@Payload TransferChecked transfer) {
        if (isBlocked(transfer.getAmount())) {
            boolean exists = cardService.existsByTransferId(transfer.getAccountDetailsId());
            if (exists) {
                update(CARD, transfer, transfer.getId());
                verdictProducer.sendVerdict(transfer.getId(), BLOCKED, transfer.getPurpose());
            } else {
                create(CARD, transfer);
                verdictProducer.sendVerdict(transfer.getId(), BLOCKED, transfer.getPurpose());
            }
        } else if (!isBlocked(transfer.getAmount())
                && cardService.existsByTransferId(transfer.getAccountDetailsId())) {
            cleanUpdate(CARD, transfer, transfer.getId());
            verdictProducer.sendVerdict(transfer.getId(), ALLOWED, transfer.getPurpose());
        }
    }

    @KafkaListener(topics = "transfer.phone", groupId = "transfer-group")
    public void listenPhone(@Payload TransferChecked transfer) {
        if (isBlocked(transfer.getAmount())) {
            boolean exists = phoneService.existsByTransferId(transfer.getAccountDetailsId());
            if (exists) {
                update(PHONE, transfer, transfer.getId());
                verdictProducer.sendVerdict(transfer.getId(), BLOCKED, transfer.getPurpose());
            } else {
                create(PHONE, transfer);
                verdictProducer.sendVerdict(transfer.getId(), BLOCKED, transfer.getPurpose());
            }
        } else if (!isBlocked(transfer.getAmount())
                && phoneService.existsByTransferId(transfer.getAccountDetailsId())) {
            cleanUpdate(PHONE, transfer, transfer.getId());
            verdictProducer.sendVerdict(transfer.getId(), ALLOWED, transfer.getPurpose());
        }
    }

    private void create(String type, TransferChecked t) {
        final Object dto = factory.createDto(t, type);
        switch (type) {
            case "ACCOUNT" -> accountService.createTransfer((SuspiciousAccountTransferDto) dto);
            case "CARD" -> cardService.createTransfer((SuspiciousCardTransferDto) dto);
            case "PHONE" -> phoneService.createTransfer((SuspiciousPhoneTransferDto) dto);
            default -> throw new IllegalArgumentException("Неизвестный тип перевода: " + type);
        }
        log.info("Создана подозрительная транзакция типа {}", type);
    }

    private void update(String type, TransferChecked t, Long id) {
        final Object dto = factory.createDto(t, type);
        switch (type) {
            case "ACCOUNT" -> accountService.updateTransfer(id, (SuspiciousAccountTransferDto) dto);
            case "CARD" -> cardService.updateTransfer(id, (SuspiciousCardTransferDto) dto);
            case "PHONE" -> phoneService.updateTransfer(id, (SuspiciousPhoneTransferDto) dto);
            default -> throw new IllegalArgumentException("Неизвестный тип перевода: " + type);
        }
        log.info("Обновлена подозрительная транзакция типа {}", type);
    }

    private void cleanUpdate(String type, TransferChecked t, Long id) {
        final Object dto = factory.updateDto(t, type);
        switch (type) {
            case "ACCOUNT" -> accountService.updateTransfer(id, (SuspiciousAccountTransferDto) dto);
            case "CARD" -> cardService.updateTransfer(id, (SuspiciousCardTransferDto) dto);
            case "PHONE" -> phoneService.updateTransfer(id, (SuspiciousPhoneTransferDto) dto);
            default -> throw new IllegalArgumentException("Неизвестный тип перевода: " + type);
        }
        log.info("Обновлена проверенная транзакция типа {}", type);
    }

    private boolean isBlocked(BigDecimal amount) {
        return amount.compareTo(LIMIT) > 0;
    }
}



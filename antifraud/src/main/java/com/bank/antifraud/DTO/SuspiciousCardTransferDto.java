package com.bank.antifraud.DTO;

import lombok.Data;
/**
 * Передача данных о подозрительных карточных переводах.
 */
@Data
public class SuspiciousCardTransferDto {
    private Long cardTransferId;

    private Boolean isBlocked;

    private Boolean isSuspicious;

    private String blockedReason;

    private String suspiciousReason;
}

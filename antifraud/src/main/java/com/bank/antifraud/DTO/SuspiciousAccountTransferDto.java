package com.bank.antifraud.DTO;

import lombok.Data;
/**
 * Передача данных о подозрительных переводах по счетам.
 */
@Data
public class SuspiciousAccountTransferDto {
    private Long accountTransferId;

    private Boolean isBlocked;

    private Boolean isSuspicious;

    private String blockedReason;

    private String suspiciousReason;
}

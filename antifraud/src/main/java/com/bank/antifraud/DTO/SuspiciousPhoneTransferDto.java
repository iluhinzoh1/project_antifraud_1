package com.bank.antifraud.DTO;

import lombok.Data;
/**
 * Передача данных о подозрительных переводах по телефону.
 */
@Data
public class SuspiciousPhoneTransferDto {
    private Long phoneTransferId;

    private Boolean isBlocked;

    private Boolean isSuspicious;

    private String blockedReason;

    private String suspiciousReason;
}


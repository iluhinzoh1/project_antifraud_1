package com.bank.antifraud.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
/**
 * Передача данных о подозрительных переводах по телефону.
 */
@Data
@Schema(description = "Метаданные подозрительного перевода по номеру")
public class SuspiciousPhoneTransferDto {
    @Schema(description = "ID перевода по номеру", example = "123")
    private Long phoneTransferId;

    @Schema(description = "Заблокирован/нет", example = "true")
    private Boolean isBlocked;

    @Schema(description = "Подозрительный перевод", example = "true")
    private Boolean isSuspicious;

    @Schema(description = "Причина блокировки", example = "Сумма превышает лимит")
    private String blockedReason;

    @Schema(description = "Подозрительная причина", example = "Большой перевод")
    private String suspiciousReason;
}


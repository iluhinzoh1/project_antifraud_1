package com.bank.antifraud.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
/**
 * Передача данных о подозрительных карточных переводах.
 */
@Data
@Schema(description = "Метаданные подозрительного перевода по карте")
public class SuspiciousCardTransferDto {
    @Schema(description = "ID перевода по карте", example = "123")
    private Long cardTransferId;

    @Schema(description = "Заблокирован/нет", example = "true")
    private Boolean isBlocked;

    @Schema(description = "Подозрительный перевод", example = "true")
    private Boolean isSuspicious;

    @Schema(description = "Причина блокировки", example = "Сумма превышает лимит")
    private String blockedReason;

    @Schema(description = "Подозрительная причина", example = "Большой перевод")
    private String suspiciousReason;
}

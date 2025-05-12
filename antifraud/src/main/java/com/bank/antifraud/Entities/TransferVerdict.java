package com.bank.antifraud.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс для отправки подозрительных
 * данных в другие микросервисы в понятном формате (id, заблокали/не заблокали, причина)
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferVerdict {
    private Long transferId;
    private String verdict; // "ALLOWED", "BLOCKED"
    private String reason;
}

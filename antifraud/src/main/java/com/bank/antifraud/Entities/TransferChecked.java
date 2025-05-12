package com.bank.antifraud.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Класс для приемки данных из другого микросервиса (transfer)
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferChecked {
    private Long transferId;
    private String transferType;
    private Long number;
    private BigDecimal amount;
    private String purpose;
    private Long accountDetailsId;
}

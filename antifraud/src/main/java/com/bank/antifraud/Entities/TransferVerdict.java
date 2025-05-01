package com.bank.antifraud.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferVerdict {
    private Long transferId;
    private String verdict; // "ALLOWED", "BLOCKED"
    private String reason;
}

package com.bank.antifraud.DTO;

import lombok.Data;
/**
 * Передача информации об изменениях.
 */
@Data
public class AuditDto {
    private String entityType;

    private String operationType;

    private String createdBy;

    private String entityJson;
}

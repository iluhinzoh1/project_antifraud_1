package com.bank.antifraud.DTO;

import lombok.Data;

import java.time.LocalDateTime;


/**
 * Передача информации об изменениях.
 */
@Data
public class AuditDto {
    private String entityType;

    private String operationType;

    private String createdBy;

    private String modifiedBy;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String newEntityJson;

    private String entityJson;

}

package com.bank.antifraud.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Передача информации об изменениях.
 */

@Data
@Schema(description = "Метаданные информации об изменениях")
public class AuditDto {
    @Schema(description = "ID", example = "123")
    private Long id;

    @Schema(description = "Название изначально сущности", example = "SuspiciousCardTransfer")
    private String entityType;

    @Schema(description = "Название выполненной операции", example = "CREATE")
    private String operationType;

    @Schema(description = "Кем выполнена транзакция", example = "AnonymousUser")
    private String createdBy;

    @Schema(description = "Кем изменена транзакция", example = "AnonymousUser")
    private String modifiedBy;

    @Schema(description = "Дата создания транзакции", example = "2024-05-01T13:12:17")
    private LocalDateTime createdAt;

    @Schema(description = "Дата изменения транзакции", example = "2024-05-01T13:12:17")
    private LocalDateTime modifiedAt;

    @Schema(description = "Название измененной сущности в JSON", example = "{id:1, isReason:true}")
    private String newEntityJson;

    @Schema(description = "Название созданной сущности в JSON", example = "{id:1, isReason:true}")
    private String entityJson;

}

package com.bank.antifraud.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Класс для предоставления информации об ошибке
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Метаданные для отображения ошибки")
public class ErrorResponse {
    @Schema(description = "Время перехвата ошибки", example = "2024-05-01T13:12:17")
    private LocalDateTime timestamp;

    @Schema(description = "Статус ошибки", example = "404")
    private int status;

    @Schema(description = "Название ошибки", example = "IllegalArgumentException")
    private String error;

    @Schema(description = "Ответ ошибки", example = "Неверно введенные данные")
    private String message;

    @Schema(description = "Путь до возникновения ошибки", example = "main/somethingMethod")
    private String path;
}

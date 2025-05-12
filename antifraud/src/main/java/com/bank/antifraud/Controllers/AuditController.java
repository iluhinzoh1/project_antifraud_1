package com.bank.antifraud.Controllers;

import com.bank.antifraud.DTO.AuditDto;
import com.bank.antifraud.Services.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Класс для отображения изменения аудита (плюсом отображение в swagger)
 */

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
@Tag(name = "Аудит транзакций", description = "Отображение всех аудитов перехваченных в методах create/update")
public class AuditController {
    private final AuditService auditService;

    @GetMapping
    @Operation(
            summary = "Вызвать все аудиты",
            description = "Вызывает все перехваченные аудиты в классе AuditAspect"
    )
    @ApiResponse(responseCode = "200", description = "Вызвана операция")
    public List<AuditDto> getAll() {
        return auditService.getAllAuditLogs();
    }
}

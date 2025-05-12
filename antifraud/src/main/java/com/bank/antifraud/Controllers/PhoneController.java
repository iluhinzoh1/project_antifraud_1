package com.bank.antifraud.Controllers;

import com.bank.antifraud.DTO.SuspiciousPhoneTransferDto;
import com.bank.antifraud.Services.SuspiciousTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Класс для отображения контроллеров по телефону (плюсом отображение в swagger)
 */

@RestController
@RequestMapping("/phone-transfer")
@Tag(name = "переводы по телефону", description = "Управление подозрительными переводами по номеру")
public class PhoneController extends AbstractController<SuspiciousPhoneTransferDto> {
    public PhoneController(SuspiciousTransferService<SuspiciousPhoneTransferDto> service) {
        super(service);
    }

    @Override
    @Operation(
            summary = "Создать запись",
            description = "Сохраняет подозрительную запись по номеру"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Запись создана"),
            @ApiResponse(responseCode = "400", description = "Неверные данные")
    })
    @PostMapping
    public ResponseEntity<SuspiciousPhoneTransferDto> create(@Valid @RequestBody
                                                            @Parameter(description = "Данные для создания")
                                                            SuspiciousPhoneTransferDto dto) {
        return super.create(dto);
    }

    @Override
    @Operation(
            summary = "Изменить запись",
            description = "Изменяет подозрительную запись по номеру"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Запись изменена"),
            @ApiResponse(responseCode = "204", description = "Нету данных"),
            @ApiResponse(responseCode = "400", description = "Неверные данные"),
            @ApiResponse(responseCode = "401", description = "Неавторизованный пользователь")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SuspiciousPhoneTransferDto> update(@PathVariable
                                                            Long id, @Valid @RequestBody
                                                            SuspiciousPhoneTransferDto dto) {
        return super.update(id, dto);
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить запись",
            description = "Удаляет подозрительную запись по номеру"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запись удалена"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена"),
            @ApiResponse(responseCode = "400", description = "Неверные данные")
    })
    public ResponseEntity<Long> delete(
            @Parameter(description = "ID записи", example = "123")
            @PathVariable Long id
    ) {
        return super.delete(id);
    }

    @Override
    @Operation(
            summary = "Получить все записи",
            description = "Получает подозрительные записи по номеру"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запись получена"),
            @ApiResponse(responseCode = "204", description = "Нету данных"),
            @ApiResponse(responseCode = "401", description = "Неавторизованный пользователь"),
    })
    @GetMapping
    public List<? extends SuspiciousPhoneTransferDto> getAll() {
        return super.getAll();
    }

    @Override
    @Operation(
            summary = "Получить запись по id",
            description = "Получает подозрительную запись по номеру"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запись получена"),
            @ApiResponse(responseCode = "204", description = "Нету данных"),
            @ApiResponse(responseCode = "401", description = "Неавторизованный пользователь"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<SuspiciousPhoneTransferDto> getById(
            @PathVariable Long id) {
        return super.getById(id);
    }
}

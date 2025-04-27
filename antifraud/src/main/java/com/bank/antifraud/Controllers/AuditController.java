package com.bank.antifraud.Controllers;

import com.bank.antifraud.DTO.AuditDto;
import com.bank.antifraud.Services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {
    private final AuditService auditService;

    @GetMapping
    public List<AuditDto> getAll() {
        return auditService.getAllAuditLogs();
    }
}

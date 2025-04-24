package com.bank.antifraud.Controllers;

import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
import com.bank.antifraud.Entities.SuspiciousCardTransfer;
import com.bank.antifraud.Kafka.SuspiciousTransferProducer;
import com.bank.antifraud.Services.SuspiciousCardTransferServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardController {
    private final SuspiciousTransferProducer producer;
    private final SuspiciousCardTransferServiceImpl service;

    @PostMapping("kafka/send")
    public String send(@RequestBody String message) {
        producer.sendCreate(message);
        return "success";
    }

    @GetMapping()
    public List<SuspiciousCardTransferDto> getAllCardTransfers() {
        return service.getAllTransfers();
    }

    @PostMapping("/create")
    public SuspiciousCardTransferDto createCardTransfer(@RequestBody SuspiciousCardTransferDto dto) {
        return service.createTransfer(dto);
    }
}

package com.bank.antifraud.Controllers;

import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
import com.bank.antifraud.Services.SuspiciousTransferService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card-transfer")
public class CardController extends AbstractController<SuspiciousCardTransferDto> {
    public CardController(SuspiciousTransferService<SuspiciousCardTransferDto> service) {
        super(service);
    }
}

package com.bank.antifraud.Controllers;

import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import com.bank.antifraud.Services.SuspiciousTransferService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account-transfer")
public class AccountController extends AbstractController<SuspiciousAccountTransferDto> {
    public AccountController(SuspiciousTransferService<SuspiciousAccountTransferDto> service) {
        super(service);
    }
}

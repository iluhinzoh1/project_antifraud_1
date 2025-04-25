package com.bank.antifraud.Controllers;

import com.bank.antifraud.DTO.SuspiciousPhoneTransferDto;
import com.bank.antifraud.Services.SuspiciousTransferService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phone-transfer")
public class PhoneController extends AbstractController<SuspiciousPhoneTransferDto> {
    public PhoneController(SuspiciousTransferService<SuspiciousPhoneTransferDto> service) {
        super(service);
    }
}

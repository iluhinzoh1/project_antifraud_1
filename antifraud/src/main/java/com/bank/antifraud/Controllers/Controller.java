package com.bank.antifraud.Controllers;

import com.bank.antifraud.Kafka.SuspiciousTransferProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final SuspiciousTransferProducer producer;

    @PostMapping("kafka/send")
    public String send(@RequestBody String message) {
        producer.sendMessage(message);
        return "success";
    }
}

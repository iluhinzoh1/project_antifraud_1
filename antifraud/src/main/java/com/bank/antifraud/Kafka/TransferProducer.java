package com.bank.antifraud.Kafka;

import com.bank.antifraud.Entities.TransferVerdict;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransferProducer {
    private final KafkaTemplate<String, TransferVerdict> kafkaTemplate;

    public void sendVerdict(Long transferId, String verdict, String reason) {
        final TransferVerdict payload = new TransferVerdict(transferId, verdict, reason);
        kafkaTemplate.send("transfers.verdict", payload);
        log.info("Sent verdict: {}", payload);
    }
}

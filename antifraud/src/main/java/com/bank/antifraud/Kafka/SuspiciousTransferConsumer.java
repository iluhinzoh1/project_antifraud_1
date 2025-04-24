package com.bank.antifraud.Kafka;

import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SuspiciousTransferConsumer {

    @KafkaListener(
            topics = {"suspicious-transfers.create",
                    "suspicious-transfers.update",
                    "suspicious-transfers.delete",
                    "suspicious-transfers.get"},
            groupId = "anti-fraud-group")
    public void onMessage(ConsumerRecord<String,String> record) {
        System.out.printf("Получили из топика %s: %s%n",
                record.topic(), record.value());
        // тут вы можете вызвать соответствующий метод сервиса
        // для create/update/delete/get
    }
}

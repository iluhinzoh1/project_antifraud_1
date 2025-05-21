package com.bank.antifraud.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Класс для создания новых топиков и партиций
 */

@Configuration
public class KafkaConfig {

    @Bean
    public List<NewTopic> newTopics() {
        return List.of(
                new NewTopic("suspicious-transfers.create", 1, (short) 1),
                new NewTopic("suspicious-transfers.update", 1, (short) 1),
                new NewTopic("suspicious-transfers.delete", 1, (short) 1),
                new NewTopic("suspicious-transfers.get", 1, (short) 1),
                new NewTopic("audit-events", 1, (short) 1)
        );
    }
}

package com.example.demo.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

// 負責把 spin 紀錄「發送」到 Kafka topic
@Component
public class SpinRecordProducer {

    // 所有 spin 紀錄都發到這個 topic
    public static final String TOPIC = "spin-records";

    // KafkaTemplate 是 Spring 提供的發送工具（加了 spring-kafka 就自動有）
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SpinRecordProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(SpinRecordMessage message) {
        // 把訊息丟到 topic，馬上返回（不等 consumer 處理）
        kafkaTemplate.send(TOPIC, message);
    }
}

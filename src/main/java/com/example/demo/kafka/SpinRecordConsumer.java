package com.example.demo.kafka;

import com.example.demo.entity.SpinRecord;
import com.example.demo.repository.SpinRecordRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpinRecordConsumer {

    private final SpinRecordRepository spinRecordRepository;

    public SpinRecordConsumer(SpinRecordRepository spinRecordRepository) {
        this.spinRecordRepository = spinRecordRepository;
    }

    // 一次 poll 收到一整批訊息
    @KafkaListener(topics = SpinRecordProducer.TOPIC, groupId = "spin-record-group")
    public void consume(List<SpinRecordMessage> messages) {
        // 把整批訊息轉成 entity
        List<SpinRecord> records = messages.stream()
                .map(m -> new SpinRecord(
                        m.getUserId(), m.getSymbols(), m.getBet(),
                        m.getWin(), m.isFreeGame(), m.getFreeGameRound()))
                .toList();
        // 一次寫入整批，減少 MySQL 往返
        spinRecordRepository.saveAll(records);
    }
}

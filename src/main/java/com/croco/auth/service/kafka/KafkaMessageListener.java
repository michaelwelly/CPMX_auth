package com.croco.auth.service.kafka;


import com.croco.auth.kafka.model.KafkaMessage;
import com.croco.auth.service.KafkaMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaMessageListener {
    private final KafkaMessageService kafkaMessageService;

    @Autowired
    public KafkaMessageListener(KafkaMessageService kafkaMessageService) {
        this.kafkaMessageService = kafkaMessageService;
    }

    @KafkaListener(topics = "${spring.kafka.data.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(KafkaMessage data) {
        System.out.println("Received Kafka message: " + data);
        kafkaMessageService.handleMessage(data);
    }
}
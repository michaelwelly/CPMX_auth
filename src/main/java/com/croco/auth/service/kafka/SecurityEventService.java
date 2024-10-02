package com.croco.auth.service.kafka;

import com.croco.auth.dto.AuthResponseDTO;
import com.croco.auth.dto.SecurityEventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SecurityEventService {
    @Value("${spring.kafka.security.topic}")
    private String securityTopic;

    private final List<SecurityEventDTO> events = new CopyOnWriteArrayList<>();

//    @KafkaListener(topics = "${spring.kafka.security.topic}", groupId = "crmx")
//    public void listen(SecurityEventDTO event) {
//        events.add(event); // Сохраняем событие в список
//    }


    @Autowired
    @Qualifier("securityKafkaTemplate")
    private KafkaTemplate<String, SecurityEventDTO> kafkaTemplate;

    public void logEvent(SecurityEventDTO securityEventDTO) {
        kafkaTemplate.send(securityTopic, securityEventDTO);
    }

    public List<SecurityEventDTO> getAllEvents() {
        return new ArrayList<>(events); // Возвращаем копию списка событий
    }
}
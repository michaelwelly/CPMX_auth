package com.croco.auth.service.kafka;

import com.croco.auth.dto.AuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthResponseService {

    @Value("${spring.kafka.response.topic}")
    private String responseTopic;

    @Autowired
    private KafkaTemplate<String, AuthResponseDTO> kafkaTemplate;

    public void sendAuthResponse(AuthResponseDTO authResponse) {
        kafkaTemplate.send(responseTopic, authResponse);
    }
}
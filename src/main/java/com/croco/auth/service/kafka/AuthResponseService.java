package com.croco.auth.service.kafka;

import com.croco.auth.dto.AuthResponseDTO;
import com.croco.auth.entity.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthResponseService {

    @Value("${spring.kafka.response.topic}")
    private String responseTopic;

    @Autowired
    @Qualifier("authResponseKafkaTemplate")
    private KafkaTemplate<String, AuthResponseDTO> kafkaTemplate;

    public void sendAuthResponse(AuthResponseDTO authResponse) {
        kafkaTemplate.send(responseTopic, authResponse);
    }

    public void sendAnauthorizedAuthResponse(String uuid, String loginName) {
        AuthResponseDTO authResponse = new AuthResponseDTO(uuid,null, loginName,null, UserStatus.DISABLED,null );
        kafkaTemplate.send(responseTopic, authResponse);
    }
}
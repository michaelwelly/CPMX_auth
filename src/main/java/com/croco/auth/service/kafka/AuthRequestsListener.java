package com.croco.auth.service.kafka;

import com.croco.auth.dto.AuthRequestDTO;
import com.croco.auth.dto.AuthResponseDTO;
import com.croco.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AuthRequestsListener {

    private final AuthService authenticationService;
    private final AuthResponseService authResponseService;

    public AuthRequestsListener(AuthService authenticationService, AuthResponseService authResponseService) {
        this.authenticationService = authenticationService;
        this.authResponseService = authResponseService;
    }

    @KafkaListener(topics = "${spring.kafka.request.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(AuthRequestDTO authRequest) {
        // Обработка полученного AuthRequestDTO
        log.info("Received AuthRequest: " + authRequest);
        AuthResponseDTO responseDTO = null;
        try{
            responseDTO = authenticationService.signIn(authRequest);
        }catch(Exception e) {
            authResponseService.sendAnauthorizedAuthResponse(authRequest.getUuid(), authRequest.getLoginName());
        }
        if(responseDTO != null){
            authResponseService.sendAuthResponse(responseDTO);
        }

    }
}
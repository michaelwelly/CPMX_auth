package com.croco.auth.api.controllers;

import com.croco.auth.dto.SecurityEventDTO;
import com.croco.auth.service.kafka.SecurityEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SecurityEventController {

    private final SecurityEventService securityEventService;

    @Autowired
    public SecurityEventController(SecurityEventService securityEventService) {
        this.securityEventService = securityEventService;
    }

    @GetMapping("/api/security-events")
    public List<SecurityEventDTO> getSecurityEvents() {
        return securityEventService.getAllEvents(); // Возвращаем все события
    }
}
package com.croco.auth.service;

import com.croco.auth.dto.EventDTO;

public interface LoggingService {
    void logEvent(EventDTO eventDTO);
}
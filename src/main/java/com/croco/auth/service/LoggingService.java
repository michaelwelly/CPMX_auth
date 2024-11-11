package com.croco.auth.service;

import com.croco.auth.dto.EventDTO;
import com.croco.auth.entity.EventType;
import com.croco.auth.entity.SystemPart;

public interface LoggingService {
    void logEvent(EventDTO eventDTO, EventType eventType, SystemPart systemPart);
}
package com.croco.auth.service.impl;

import com.croco.auth.dto.EventDTO;
import com.croco.auth.entity.Event;
import com.croco.auth.entity.EventType;
import com.croco.auth.entity.SystemPart;
import com.croco.auth.entity.User;
import com.croco.auth.repository.EventRepository;
import com.croco.auth.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoggingServiceImpl implements LoggingService {

    private final EventRepository eventRepository;

    public void logEvent(EventDTO eventDTO) {
        Event event = new Event();
        event.setUser(new User(eventDTO.getUserId()));
        event.setEventType(EventType.valueOf(eventDTO.getEventType())); // Преобразование строки в Enum
        event.setSystemPart(SystemPart.valueOf(eventDTO.getSystemPart())); // Преобразование строки в Enum
        event.setFieldName(eventDTO.getFieldName());
        event.setFieldValueBefore(eventDTO.getFieldValueBefore());
        event.setFieldValueAfter(eventDTO.getFieldValueAfter());
        event.setChangeDate(LocalDateTime.now());
        event.setChangeReason(eventDTO.getChangeReason());
        event.setEventCode(eventDTO.getEventCode());
        event.setDescription(eventDTO.getDescription());

        eventRepository.save(event);
    }

    public void logInfo(Long userId, String message) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setUserId(userId);
        eventDTO.setEventType("INFO");
        eventDTO.setSystemPart("AUTH");
        eventDTO.setDescription(message);
        logEvent(eventDTO);
    }

    public void logError(Long userId, String message) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setUserId(userId);
        eventDTO.setEventType("ERROR");
        eventDTO.setSystemPart("AUTH");
        eventDTO.setDescription(message);
        logEvent(eventDTO);
    }
}

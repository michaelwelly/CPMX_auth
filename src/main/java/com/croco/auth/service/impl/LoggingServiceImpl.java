package com.croco.auth.service.impl;

import com.croco.auth.dto.EventDTO;
import com.croco.auth.entity.Event;
import com.croco.auth.entity.EventType;
import com.croco.auth.entity.SystemPart;
import com.croco.auth.entity.User;
import com.croco.auth.repository.EventRepository;
import com.croco.auth.repository.UserRepository;
import com.croco.auth.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoggingServiceImpl implements LoggingService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    public void logEvent(EventDTO eventDTO, EventType eventType, SystemPart systemPart) {
        Event event = new Event();
        User user = userRepository.findByLoginName(eventDTO.getFieldName()).get();
        event.setUser(user);
        event.setEventType(eventType);
        event.setSystemPart(systemPart);
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
        logEvent(eventDTO, EventType.INFO, SystemPart.AUTH);
    }

    public void logError(Long userId, String message) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setUserId(userId);
        eventDTO.setEventType("ERROR");
        eventDTO.setSystemPart("AUTH");
        eventDTO.setDescription(message);
        logEvent(eventDTO, EventType.ERROR, SystemPart.AUTH);
    }
}

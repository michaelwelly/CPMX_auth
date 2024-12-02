package com.croco.auth.service;

import com.croco.auth.dto.SecurityEventDTO;
import com.croco.auth.entity.EventType;
import com.croco.auth.entity.SystemPart;
import com.croco.auth.kafka.model.CRUDAction;
import com.croco.auth.kafka.model.KafkaMessage;
import com.croco.auth.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KafkaMessageService {
    private final EventMapper eventMapper;
    private final LoggingService loggingService;
    private final RequestValidator requestValidator;

    @Autowired
    public KafkaMessageService(EventMapper eventMapper,
                               LoggingService loggingService,
                               RequestValidator requestValidator) {
        this.eventMapper = eventMapper;
        this.loggingService = loggingService;
        this.requestValidator = requestValidator;
    }

    public void handleMessage(KafkaMessage message) {
        // Валидация входящего сообщения
        requestValidator.validate(message.version, message.authToken, message.md5Signature);

        // Создание события для логирования
        SecurityEventDTO event = new SecurityEventDTO(
                getEventType(message.action),
                message.entityType.toString(),
                LocalDateTime.now().toString(),
                String.format("Action: %s, EntityType: %s, ElementId: %d",
                        message.action,
                        message.entityType,
                        message.elementId)
        );

        // Логирование события
        loggingService.logEvent(
                eventMapper.toEventDTO(event),
                getEventTypeByAction(message.action),
                SystemPart.AUTH
        );
    }

    private String getEventType(CRUDAction action) {
        return switch (action) {
            case CREATE -> "ENTITY_CREATED";
            case UPDATE -> "ENTITY_UPDATED";
            case DELETE -> "ENTITY_DELETED";
            case GET -> "ENTITY_ACCESSED";
        };
    }

    private EventType getEventTypeByAction(CRUDAction action) {
        return switch (action) {
            case CREATE, UPDATE, DELETE -> EventType.SECURITY;
            case GET -> EventType.INFO;
        };
    }
}
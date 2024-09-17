package com.croco.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class EventDTO {
    private Long userId;
    private String eventType;
    private String systemPart;
    private String fieldName;
    private String fieldValueBefore;
    private String fieldValueAfter;
    private String changeReason;
    private Integer eventCode;
    private String description;

    // Геттеры и сеттеры
}
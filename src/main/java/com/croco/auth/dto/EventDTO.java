package com.croco.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventDTO {
    private Long id;
    private Long userId;
    private String eventType;
    private String systemPart;
    private String fieldName;
    private String fieldValueBefore;
    private String fieldValueAfter;
    private LocalDateTime changeDate;
    // getters and setters
}
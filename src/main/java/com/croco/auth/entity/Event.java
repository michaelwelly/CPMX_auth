package com.croco.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
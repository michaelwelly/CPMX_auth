package com.croco.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "event")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type_id")
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    @Column(name = "system_part_id")
    private SystemPart systemPart;

    @Column(name = "field_name_str")
    private String fieldName;

    @Column(name = "field_value_before_str")
    private String fieldValueBefore;

    @Column(name = "field_value_after_str")
    private String fieldValueAfter;

    @Column(name = "change_date_dttm")
    private LocalDateTime changeDate;

    @Column(name = "change_reason_text")
    private String changeReason;

    @Column(name = "event_code_num")
    private Integer eventCode;

    @Column(name = "description_json")
    private String description;

    // Геттеры и сеттеры
}
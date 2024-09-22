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
@Table(name = "user_session")
@Getter
@Setter
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "start_session_dttm")
    private LocalDateTime startSession;

    @Column(name = "end_session_dttm")
    private LocalDateTime endSession;

    @Column(name = "device_str")
    private String device;

    @Column(name = "session_token")
    private String sessionToken;

    @Column(name = "ip_str")
    private String ip;
}
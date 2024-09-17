package com.croco.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_name_str", nullable = false)
    private String loginName;

    @Column(name = "password_byte", nullable = false)
    private byte[] password;

    @Column(name = "user_description_json")
    private String userDescription; // Можно использовать JSON-строку или объект

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status_id")
    private UserStatus userStatus;

    @Column(name = "create_date_dttm")
    private LocalDateTime createDate;

    @Column(name = "last_login_date_dttm")
    private LocalDateTime lastLoginDate;

    @Column(name = "password_set_date_dttm")
    private LocalDateTime passwordSetDate;

    public User(Long id) {
        this.id = id;
    }
}
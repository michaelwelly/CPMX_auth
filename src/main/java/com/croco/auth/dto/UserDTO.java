package com.croco.auth.dto;

import com.croco.auth.entity.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String loginName;
    private String userDescription;
    private UserStatus userStatus;
    private LocalDateTime createDate;
    private LocalDateTime lastLoginDate;

    // Геттеры и сеттеры
}
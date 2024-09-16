package com.croco.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDTO {
    private Long userId;
    private String loginName;
    private String userDescription;
    private String userStatus;
    private String permissionsMask; // Маска прав
    private String settings; // Настройки пользователя
    private String sessionToken; // Сессионный токен

    // getters and setters
}
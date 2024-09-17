package com.croco.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {
    private String subsystemName;
    private String loginName;
    private String hashedPassword;

    // Геттеры и сеттеры
}

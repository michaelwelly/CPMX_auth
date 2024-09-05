package com.croco.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String loginName;
    private String hashedPassword;
    // getters and setters
}
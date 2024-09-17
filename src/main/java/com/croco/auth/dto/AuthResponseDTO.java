package com.croco.auth.dto;

import com.croco.auth.entity.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDTO {
    private Long userId;
    private String loginName;
    private String userDescription;
    private UserStatus userStatus;
    private String sessionToken;
}
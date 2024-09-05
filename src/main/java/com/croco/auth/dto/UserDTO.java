package com.croco.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String loginName;
    private String userDescription;
    private String userStatus;
    // getters and setters
}

package com.croco.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserSessionDTO {
    private Long id;
    private Long userId;
    private LocalDateTime startSession;
    private LocalDateTime endSession;
    private String device;
    private String ip;


}
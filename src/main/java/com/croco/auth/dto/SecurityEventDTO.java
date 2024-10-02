package com.croco.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityEventDTO {
    private String eventType; // Тип события (например, "LOGIN_ATTEMPT", "ACCESS_CHANGE")
    private String loginName; // Имя пользователя
    private String timestamp; // Время события
    private String details; // Дополнительные детали события
}
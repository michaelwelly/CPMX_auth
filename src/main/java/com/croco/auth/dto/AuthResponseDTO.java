package com.croco.auth.dto;

import com.croco.auth.entity.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ c токеном доступа")
public class AuthResponseDTO {
    @Schema(description = "Id пользователя")
    private Long userId;
    @Schema(description = "Логин пользователя")
    private String loginName;
    @Schema(description = "Описание пользователя")
    private String userDescription;
    @Schema(description = "Статус пользователя")
    private UserStatus userStatus;
    @Schema(description = "Токен доступа", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj...")
    private String sessionToken;

    public AuthResponseDTO(String jwt) {
        this.sessionToken = jwt;
    }
}
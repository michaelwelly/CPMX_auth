package com.croco.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Schema(description = "Запрос на авторизацию")
public class AuthRequestDTO {

    @Schema(description = "Имя пользователя", example = "Jon")
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String loginName;

    @Schema(description = "Имя подсистемы", example = "operatorService")
    @Size(min = 5, max = 255, message = "Имя подсистем должно содержать от 5 до 255 символов")
    @NotBlank(message = "Имя подсистемы не может быть пустыми")
    private String subsystemName;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String hashedPassword;
}

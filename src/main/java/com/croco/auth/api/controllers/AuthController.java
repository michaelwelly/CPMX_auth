package com.croco.auth.api.controllers;

import com.croco.auth.dto.AuthResponseDTO;
import com.croco.auth.dto.LoginRequest;
import com.croco.auth.dto.UserDTO;
import com.croco.auth.service.AuthService;
import com.croco.auth.service.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// AuthController.java
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(
            @RequestParam String subsystemName,
            @RequestParam String loginName,
            @RequestParam String hashedPassword) {

        AuthResponseDTO response = authService.authenticate(subsystemName, loginName, hashedPassword);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody UserDTO userDTO) {
        authService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO userDTO = authService.getUser(id);
        return ResponseEntity.ok(userDTO);
    }

    @Autowired
    private JwtTokenProvider jwtTokenProvider; // Сервис для работы с JWT

    @GetMapping("/validate")
    public ResponseEntity<AuthResponseDTO> validateToken(@RequestHeader("Authorization") String token) {
        // Удаляем "Bearer " из заголовка, если он присутствует
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Проверка валидности токена
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Токен недействителен
        }

        // Извлечение информации о пользователе из токена
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        UserDTO userDTO = authService.getUser(userId); // Получаем информацию о пользователе по его ID

        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Пользователь не найден
        }

        // Формируем ответ
        AuthResponseDTO response = new AuthResponseDTO();
        response.setUserId(userDTO.getId());
        response.setLoginName(userDTO.getLoginName());
        response.setUserDescription(userDTO.getUserDescription());
        response.setUserStatus(userDTO.getUserStatus());

        return ResponseEntity.ok(response); // Возвращаем информацию о пользователе
    }
}
package com.croco.auth.service.impl;

import com.croco.auth.dto.AuthRequestDTO;
import com.croco.auth.dto.AuthResponseDTO;
import com.croco.auth.dto.SecurityEventDTO;
import com.croco.auth.entity.Role;
import com.croco.auth.entity.User;
import com.croco.auth.entity.UserStatus;
import com.croco.auth.exception.UserAlreadyExistsException;
import com.croco.auth.service.AuthService;
import com.croco.auth.service.JwtService;
import com.croco.auth.service.UserService;
import com.croco.auth.service.kafka.SecurityEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityEventService securityEventService;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public AuthResponseDTO signUp(AuthRequestDTO request) {
        // Проверяем, существует ли пользователь с таким логином
        if (userService.existsByLoginName(request.getLoginName())) {
            throw new UserAlreadyExistsException("Пользователь с таким логином уже существует."); // Исключение, если пользователь уже создан
        }

        var user = User.builder()
                .loginName(request.getLoginName())
                .password(passwordEncoder.encode(request.getHashedPassword()).getBytes())
                .role(Role.ROLE_USER)
                .userStatus(UserStatus.ENABLED)
                .build();

        userService.create(user);

        // Логируем успешную регистрацию пользователя
        SecurityEventDTO event = new SecurityEventDTO("SIGNUP_SUCCESS", user.getLoginName(), LocalDateTime.now().toString(), "Пользователь успешно зарегистрирован.");
        securityEventService.logEvent(event);

        var jwt = jwtService.generateToken(user);
        return new AuthResponseDTO(user.getId(), user.getLoginName(), user.getUserDescription(), user.getUserStatus(), jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public AuthResponseDTO signIn(AuthRequestDTO request) {
        try {
            // Попытка аутентификации
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getLoginName(),
                    request.getHashedPassword()
            ));

            // Если аутентификация успешна, создайте JWT и верните ответ
            var user = (User) authentication.getPrincipal(); // Получение пользователя из аутентификации
            var jwt = jwtService.generateToken(user);

            // Логируем успешное событие входа
            SecurityEventDTO event = new SecurityEventDTO("LOGIN_SUCCESS", user.getLoginName(), LocalDateTime.now().toString(), "User logged in successfully.");
            securityEventService.logEvent(event);

            return new AuthResponseDTO(user.getId(), user.getLoginName(), user.getUserDescription(), user.getUserStatus(), jwt);
        } catch (AuthenticationException e) {
            // Логируем неудачную попытку входа
            SecurityEventDTO event = new SecurityEventDTO("LOGIN_FAILURE", request.getLoginName(), LocalDateTime.now().toString(), "Invalid username or password.");
            securityEventService.logEvent(event);
            throw new BadCredentialsException("Неверное имя пользователя или пароль.");
        }
    }

    public void changeUserAccess(Long userId, String newAccessLevel) {
        // Логика изменения прав доступа

        // Логируем событие изменения прав доступа
        SecurityEventDTO event = new SecurityEventDTO("ACCESS_CHANGE", userId.toString(), LocalDateTime.now().toString(), "User access changed to " + newAccessLevel);
        securityEventService.logEvent(event);
    }

}
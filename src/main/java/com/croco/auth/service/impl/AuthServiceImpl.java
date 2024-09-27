package com.croco.auth.service.impl;

import com.croco.auth.dto.AuthRequestDTO;
import com.croco.auth.dto.AuthResponseDTO;
import com.croco.auth.entity.Role;
import com.croco.auth.entity.User;
import com.croco.auth.entity.UserStatus;
import com.croco.auth.exception.UserAlreadyExistsException;
import com.croco.auth.service.AuthService;
import com.croco.auth.service.JwtService;
import com.croco.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

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

            return new AuthResponseDTO(user.getId(), user.getLoginName(), user.getUserDescription(), user.getUserStatus(), jwt);
        } catch (AuthenticationException e) {
            // Обработка ошибки аутентификации
            throw new BadCredentialsException("Неверное имя пользователя или пароль.");
        }
    }
}
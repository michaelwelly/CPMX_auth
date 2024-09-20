package com.croco.auth.service.impl;

import com.croco.auth.dto.AuthRequestDTO;
import com.croco.auth.dto.AuthResponseDTO;
import com.croco.auth.entity.Role;
import com.croco.auth.entity.User;
import com.croco.auth.entity.UserStatus;
import com.croco.auth.service.JwtService;
import com.croco.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {
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
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLoginName(),
                request.getHashedPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getLoginName());

        var jwt = jwtService.generateToken(user);
        return new AuthResponseDTO(jwt);
    }
}
package com.croco.auth.service;

import com.croco.auth.dto.AuthRequestDTO;
import com.croco.auth.dto.AuthResponseDTO;

public interface AuthService {
    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
     AuthResponseDTO signUp(AuthRequestDTO request);

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
     AuthResponseDTO signIn(AuthRequestDTO request);

}
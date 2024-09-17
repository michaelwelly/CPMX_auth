package com.croco.auth.service;

import com.croco.auth.dto.AuthRequestDTO;
import com.croco.auth.dto.AuthResponseDTO;
import com.croco.auth.dto.LoginRequest;
import com.croco.auth.dto.UserDTO;

public interface AuthService {
    AuthResponseDTO authenticate(AuthRequestDTO authRequest);
}
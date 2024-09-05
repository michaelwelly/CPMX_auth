package com.croco.auth.service;

import com.croco.auth.dto.AuthResponseDTO;
import com.croco.auth.dto.LoginRequest;
import com.croco.auth.dto.UserDTO;

public interface AuthService {

    String login(LoginRequest request);
    void createUser(UserDTO userDTO);
    UserDTO getUser(Long id);
    void updateUser(UserDTO userDTO);
    void deleteUser(Long userId);
    AuthResponseDTO authenticate(String subsystemName, String loginName, String hashedPassword);

}

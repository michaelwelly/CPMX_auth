package com.croco.auth.service;

import com.croco.auth.dto.UserDTO;

public interface AuthService {
    String authenticate(String login, String hashedPassword);
    void createUser(UserDTO userDTO);
    UserDTO getUser(Long userId);
    void updateUser(UserDTO userDTO);
    void deleteUser(Long userId);
}

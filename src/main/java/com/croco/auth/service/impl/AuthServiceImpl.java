package com.croco.auth.service.impl;

import com.croco.auth.dto.AuthRequestDTO;
import com.croco.auth.dto.AuthResponseDTO;
import com.croco.auth.entity.User;
import com.croco.auth.entity.UserSession;
import com.croco.auth.mapper.UserMapper;
import com.croco.auth.repository.UserRepository;
import com.croco.auth.repository.UserSessionRepository;
import com.croco.auth.service.AuthService;
import com.croco.auth.service.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO authRequest) {
        String loginName = authRequest.getLoginName();
        String rawPassword = authRequest.getHashedPassword();

        User user = userRepository.findByLoginName(loginName)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Хэширование введенного пароля
        String hashedInputPassword = hashPassword(rawPassword); // Метод для хэширования пароля

        // Проверка захешированного пароля
        //if (!user.getPassword().equals(hashedInputPassword)) {
          //  throw new RuntimeException("Неверный пароль");
      //  }

        // Генерация сессионного токена
        String sessionToken = jwtTokenProvider.generateToken(userMapper.toDto(user)); // Генерация JWT токена

        // Сохранение сессии пользователя
        UserSession userSession = new UserSession();
        userSession.setUser(user);
        userSession.setStartSession(LocalDateTime.now());
        userSession.setEndSession(null); // Сессия активна, пока пользователь не выйдет
        userSession.setDevice("Device Info"); //  информация о устройстве
        userSession.setIp("User IP"); // IP-адрес пользователя
        userSessionRepository.save(userSession); // Сохранение сессии в базе данных

        // Формирование ответа
        AuthResponseDTO response = new AuthResponseDTO();
        response.setUserId(user.getId());
        response.setLoginName(user.getLoginName());
        response.setUserDescription(user.getUserDescription());
        response.setUserStatus(user.getUserStatus());
        response.setSessionToken(sessionToken); // Возвращаем токен сессии

        return response; // Возвращаем ответ
    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка хэширования пароля", e);
        }
    }
}
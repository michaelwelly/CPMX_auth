package com.croco.auth.service.impl;

import com.croco.auth.dto.AuthResponseDTO;
import com.croco.auth.entity.User;
import com.croco.auth.repository.UserRepository;
import com.croco.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository; // Репозиторий для получения групп и прав

    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO authRequest) {
        String subsystemName = authRequest.getSubsystemName();
        String loginName = authRequest.getLoginName();
        String hashedPassword = authRequest.getHashedPassword();

        // Проверка данных подсистемы
        if (!isValidSubsystem(subsystemName)) {
            throw new RuntimeException("Недействительные данные подсистемы");
        }

        // Поиск пользователя в базе данных
        User user = userRepository.findByLoginName(loginName)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Проверка захешированного пароля
        if (!user.getPassword().equals(hashedPassword)) {
            throw new RuntimeException("Неверный пароль");
        }

        // Формирование ответа
        AuthResponseDTO response = new AuthResponseDTO();
        response.setUserId(user.getId());
        response.setLoginName(user.getLoginName());
        response.setUserDescription(user.getUserDescription());
        response.setUserStatus(user.getUserStatus());
        response.setSettings(user.getSettings());

        // Формирование маски прав
        String permissionsMask = generatePermissionsMask(user, subsystemName);
        response.setPermissionsMask(permissionsMask);

        // Генерация сессионного ключа (токена)
        String sessionToken = generateSessionToken(user);
        response.setSessionToken(sessionToken);

        return response;
    }

    private boolean isValidSubsystem(String subsystemName) {
        // Логика проверки валидности подсистемы
        return true; // Замените на реальную проверку
    }

    private String generatePermissionsMask(User user, String subsystemName) {
        // Логика формирования маски прав на основе группы и прав
        return ""; // Замените на реальную логику
    }

    private String generateSessionToken(User user) {
        // Логика генерации токена (JWT или другой механизм)
        return "generated_token"; // Замените на реальную логику
    }
}
package com.croco.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

// JwtTokenProvider.java
@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = "your_secret_key"; // Замените на ваш секретный ключ

    // Метод для валидации токена
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false; // Токен недействителен
        }
    }

    // Метод для извлечения ID пользователя из токена
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("userId", Long.class); // Предполагается, что ID пользователя хранится в токене
    }
}
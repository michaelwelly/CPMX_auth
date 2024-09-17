package com.croco.auth.service;

import com.croco.auth.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = "your_secret_key"; // Замените на ваш секретный ключ
    private final long EXPIRATION_TIME = 86400000; // 1 день

    public String generateToken(UserDTO user) {
        return Jwts.builder()
                .setSubject(user.getLoginName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
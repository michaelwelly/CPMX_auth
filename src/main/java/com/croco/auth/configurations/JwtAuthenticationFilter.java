package com.croco.auth.configurations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader("Authorization");
        String md5Signature = request.getHeader("MD5-Signature");

        if (authToken != null && authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7); // Удаляем "Bearer " из токена

            // Логика проверки MD5-Signature
            String expectedSignature = generateMD5Signature(request);
            if (md5Signature == null || !md5Signature.equals(expectedSignature)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid MD5-Signature");
                return;
            }

            // Проверка JWT
            Claims claims = Jwts.parser()
                    .setSigningKey("your_secret_key") // секретный ключ
                    .parseClaimsJws(authToken)
                    .getBody();
            String username = claims.getSubject();
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = null; // Получите UserDetails из вашего сервиса

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String generateMD5Signature(HttpServletRequest request) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            StringBuilder sb = new StringBuilder();

            // Добавьте информацию, на основе которой будет формироваться подпись
            sb.append(request.getMethod()); // Метод запроса (GET, POST и т.д.)
            sb.append(request.getRequestURI()); // URI запроса
            sb.append(request.getContentLength()); // Длина содержимого запроса
            // Добавьте другие параметры, если это необходимо, например, заголовки или тело запроса

            md.update(sb.toString().getBytes());
            byte[] digest = md.digest();

            // Преобразование байтов в шестнадцатеричную строку
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to generate MD5 signature", e);
        }
    }
}
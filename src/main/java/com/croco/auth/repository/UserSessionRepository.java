package com.croco.auth.repository;

import com.croco.auth.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    List<UserSession> findByUserId(Long userId);
}
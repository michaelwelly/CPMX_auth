package com.croco.auth.repository;

import com.croco.auth.entity.User;
import com.croco.auth.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// UserRepository.java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginName(String loginName);
}

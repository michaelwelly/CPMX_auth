package com.croco.auth.service;


import com.croco.auth.entity.User;

import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService {
    void getAdmin();
    User getCurrentUser();
    UserDetailsService userDetailsService();
    User getByUsername(String loginName);
    User create(User user);
    User save(User user);
    boolean existsByLoginName(String loginName);
}
package com.croco.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.croco.auth.entity")
@EnableJpaRepositories(basePackages = {"com.croco.auth.repository"})
public class SimpleAuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleAuthorizationApplication.class, args);
    }

}

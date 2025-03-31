package com.trustvote.votacao.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String uploadDir = "uploads/faces/";
    private double faceVerificationThreshold = 0.6;
    private int maxLoginAttempts = 3;
    private long loginAttemptTimeout = 300; // 5 minutos em segundos
    private String jwtSecret;
    private long jwtExpiration = 86400000; // 24 horas em milissegundos
} 
package com.trustvote.votacao.infrastructure.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/")
    public Map<String, String> root() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "🎉 TrustVote API está funcionando!");
        response.put("message", "Deploy realizado com sucesso no Railway");
        response.put("timestamp", Instant.now().toString());
        return response;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "TrustVote Backend");
        response.put("timestamp", Instant.now().toString());
        return response;
    }
}
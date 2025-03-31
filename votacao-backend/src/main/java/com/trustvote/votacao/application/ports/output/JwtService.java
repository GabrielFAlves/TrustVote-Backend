package com.trustvote.votacao.application.ports.output;

import com.trustvote.votacao.domain.entities.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    String generateToken(UserDetails userDetails);
    String generateToken(User user);
    String generateToken(Map<String, Object> extraClaims, User user);
    boolean isTokenValid(String token, UserDetails userDetails);
} 
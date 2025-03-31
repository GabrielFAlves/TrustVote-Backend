package com.trustvote.votacao.modules.auth.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.trustvote.votacao.domain.entities.User;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthEventPublisher {
    private final List<AuthenticationEventListener> listeners;
    
    public void publishAuthenticationSuccess(User user) {
        listeners.forEach(listener -> listener.onAuthenticationSuccess(user));
    }
    
    public void publishAuthenticationFailure(String cpf) {
        listeners.forEach(listener -> listener.onAuthenticationFailure(cpf));
    }
} 
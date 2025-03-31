package com.trustvote.votacao.modules.auth.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.trustvote.votacao.domain.entities.User;

@Slf4j
@Component
public class LoggingAuthenticationEventListener implements AuthenticationEventListener {
    
    @Override
    public void onAuthenticationSuccess(User user) {
        log.info("Autenticação bem sucedida para o usuário: {}", user.getCpf());
    }
    
    @Override
    public void onAuthenticationFailure(String cpf) {
        log.warn("Falha na autenticação para o CPF: {}", cpf);
    }
} 
package com.trustvote.votacao.application.ports.output;

import com.trustvote.votacao.domain.entities.User;

public interface AuthEventPublisher {
    void publishAuthenticationSuccess(User user);
    void publishAuthenticationFailure(String cpf);
} 
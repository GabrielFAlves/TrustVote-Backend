package com.trustvote.votacao.modules.auth.event;

import com.trustvote.votacao.domain.entities.User;

public interface AuthenticationEventListener {
    void onAuthenticationSuccess(User user);
    void onAuthenticationFailure(String cpf);
} 
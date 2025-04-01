package com.trustvote.votacao.application.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String cpf;
    private String password;
}

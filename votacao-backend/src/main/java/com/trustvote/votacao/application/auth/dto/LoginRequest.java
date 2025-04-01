package com.trustvote.votacao.application.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String cpf;
    private String password;
}

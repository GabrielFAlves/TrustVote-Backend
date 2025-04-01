package com.trustvote.votacao.application.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String name;
    private String cpf;
    private String email;
    private String phone;
    private String password;
}

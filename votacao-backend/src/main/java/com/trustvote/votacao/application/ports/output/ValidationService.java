package com.trustvote.votacao.application.ports.output;

import org.springframework.web.multipart.MultipartFile;

public interface ValidationService {
    void validateUserData(String cpf, String email, String telefone);
    void validateImage(MultipartFile image);
} 
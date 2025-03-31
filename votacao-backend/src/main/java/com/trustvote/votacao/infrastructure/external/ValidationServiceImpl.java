package com.trustvote.votacao.infrastructure.external;

import com.trustvote.votacao.application.ports.output.ValidationService;
import com.trustvote.votacao.domain.exception.RegisterValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public void validateUserData(String cpf, String email, String telefone) {
        if (cpf == null || cpf.length() != 11) {
            throw new RegisterValidationException("cpf", "CPF deve conter 11 dígitos");
        }

        if (email != null && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new RegisterValidationException("email", "Email inválido");
        }

        if (telefone != null && !telefone.matches("^\\d{10,11}$")) {
            throw new RegisterValidationException("telefone", "Telefone deve conter 10 ou 11 dígitos");
        }
    }

    @Override
    public void validateImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new RegisterValidationException("faceImage", "Imagem facial é obrigatória");
        }

        if (!image.getContentType().startsWith("image/")) {
            throw new RegisterValidationException("faceImage", "Arquivo deve ser uma imagem");
        }

        if (image.getSize() > 10 * 1024 * 1024) { // 10MB
            throw new RegisterValidationException("faceImage", "Imagem deve ter no máximo 10MB");
        }
    }
} 
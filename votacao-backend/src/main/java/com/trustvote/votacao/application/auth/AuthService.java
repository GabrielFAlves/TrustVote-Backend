package com.trustvote.votacao.application.auth;

import com.trustvote.votacao.application.auth.dto.AuthResponse;
import com.trustvote.votacao.application.auth.dto.LoginRequest;
import com.trustvote.votacao.application.auth.dto.RegisterRequest;
import com.trustvote.votacao.domain.shared.Cpf;
import com.trustvote.votacao.domain.shared.Email;
import com.trustvote.votacao.domain.shared.Phone;
import com.trustvote.votacao.domain.user.User;
import com.trustvote.votacao.domain.user.UserRepository;
import com.trustvote.votacao.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        try {
            var user = new User(
                    UUID.randomUUID(),
                    request.getName(),
                    new Cpf(request.getCpf()),
                    new Email(request.getEmail()),
                    new Phone(request.getPhone()),
                    passwordEncoder.encode(request.getPassword()),
                    false
                    // SEM walletAddress e privateKey
            );

            userRepository.save(user);
            var token = jwtService.generateToken(user.getCpf().getValue());
            return new AuthResponse(token, "Bearer", "Usuário registrado com sucesso");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao registrar usuário: " + e.getMessage(), e);
        }
    }

    public AuthResponse login(LoginRequest request) {
        var user = userRepository.findByCpf(request.getCpf())
                .orElseThrow(() -> new RuntimeException("CPF não encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        var token = jwtService.generateToken(user.getCpf().getValue());
        return new AuthResponse(token, "Bearer", "Login realizado com sucesso");
    }

}

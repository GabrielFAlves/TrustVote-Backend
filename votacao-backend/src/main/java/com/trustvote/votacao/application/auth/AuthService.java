package com.trustvote.votacao.application.auth;

import com.trustvote.votacao.domain.shared.Cpf;
import com.trustvote.votacao.domain.shared.Email;
import com.trustvote.votacao.domain.shared.Phone;
import com.trustvote.votacao.domain.user.User;
import com.trustvote.votacao.domain.user.UserRepository;
import com.trustvote.votacao.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public String register(RegisterRequest request) {
        var user = new User(
                UUID.randomUUID(),
                request.getName(),
                new Cpf(request.getCpf()),
                new Email(request.getEmail()),
                new Phone(request.getPhone()),
                passwordEncoder.encode(request.getPassword())
        );
        userRepository.save(user);
        return jwtService.generateToken(user.getCpf().getValue());
    }

    public String login(LoginRequest request) {
        var user = userRepository.findByCpf(request.getCpf())
                .orElseThrow(() -> new RuntimeException("CPF não encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        return jwtService.generateToken(user.getCpf().getValue());
    }
}

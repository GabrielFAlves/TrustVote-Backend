package com.trustvote.votacao.modules.auth.service;

import com.trustvote.votacao.domain.entities.Role;
import com.trustvote.votacao.domain.entities.User;
import com.trustvote.votacao.domain.repositories.UserRepository;
import com.trustvote.votacao.modules.auth.event.AuthEventPublisher;
import com.trustvote.votacao.application.ports.output.ImageProcessor;
import com.trustvote.votacao.application.ports.output.ValidationService;
import com.trustvote.votacao.application.ports.output.JwtService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ValidationService validationService;
    private final ImageProcessor imageProcessor;
    private final AuthEventPublisher eventPublisher;
    private final MeterRegistry meterRegistry;

    public String register(String cpf, String email, String telefone, MultipartFile faceImage) {
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            // Validações
            validationService.validateUserData(cpf, email, telefone);
            validationService.validateImage(faceImage);

            // Processamento da imagem
            byte[] imageData = imageProcessor.processImage(faceImage);
            String imagePath = imageProcessor.saveImage(imageData, cpf);

            // Criação do usuário
            var user = User.builder()
                    .cpf(cpf)
                    .email(email)
                    .telefone(telefone)
                    .role(Role.ROLE_USER)
                    .faceImagePath(imagePath)
                    .build();

            userRepository.save(user);
            eventPublisher.publishAuthenticationSuccess(user);

            return jwtService.generateToken(user);
        } catch (Exception e) {
            eventPublisher.publishAuthenticationFailure(cpf);
            throw e;
        } finally {
            sample.stop(Timer.builder("authentication.duration")
                    .tag("operation", "register")
                    .tag("cpf", cpf)
                    .register(meterRegistry));
        }
    }

    @Cacheable(value = "users", key = "#cpf")
    public String authenticate(String cpf) {
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            validationService.validateUserData(cpf, null, null);

            var user = userRepository.findByCpf(cpf)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o CPF: " + cpf));

            eventPublisher.publishAuthenticationSuccess(user);
            return jwtService.generateToken(user);
        } catch (Exception e) {
            eventPublisher.publishAuthenticationFailure(cpf);
            throw e;
        } finally {
            sample.stop(Timer.builder("authentication.duration")
                    .tag("operation", "authenticate")
                    .tag("cpf", cpf)
                    .register(meterRegistry));
        }
    }
} 
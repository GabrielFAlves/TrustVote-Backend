package com.trustvote.votacao.modules.auth.controller;

import com.trustvote.votacao.domain.exception.RegisterValidationException;
import com.trustvote.votacao.modules.auth.dto.AuthResponse;
import com.trustvote.votacao.modules.auth.dto.ErrorResponse;
import com.trustvote.votacao.modules.auth.dto.LoginRequest;
import com.trustvote.votacao.modules.auth.dto.RegisterRequest;
import com.trustvote.votacao.modules.auth.service.AuthenticationService;
import com.trustvote.votacao.application.ports.output.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Base64;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "APIs de autenticação e registro")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final ValidationService validationService;

    @Operation(summary = "Registrar novo usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            if (request == null) {
                throw new RegisterValidationException("request", "O corpo da requisição é obrigatório");
            }

            if (request.getFaceImage() == null || request.getFaceImage().trim().isEmpty()) {
                throw new RegisterValidationException("faceImage", "Imagem facial é obrigatória");
            }

            // Converte base64 para MultipartFile
            byte[] imageBytes = Base64.getDecoder().decode(request.getFaceImage());
            MultipartFile faceImage = new MultipartFile() {
                @Override
                public String getName() {
                    return "faceImage";
                }

                @Override
                public String getOriginalFilename() {
                    return "face.jpg";
                }

                @Override
                public String getContentType() {
                    return "image/jpeg";
                }

                @Override
                public boolean isEmpty() {
                    return imageBytes.length == 0;
                }

                @Override
                public long getSize() {
                    return imageBytes.length;
                }

                @Override
                public byte[] getBytes() {
                    return imageBytes;
                }

                @Override
                public java.io.InputStream getInputStream() {
                    return new java.io.ByteArrayInputStream(imageBytes);
                }

                @Override
                public void transferTo(java.io.File dest) throws IllegalStateException, java.io.IOException {
                    java.nio.file.Files.write(dest.toPath(), imageBytes);
                }
            };

            String token = authenticationService.register(
                request.getCpf(),
                request.getEmail(),
                request.getTelefone(),
                faceImage
            );

            return ResponseEntity.ok(AuthResponse.builder().token(token).build());
        } catch (RegisterValidationException e) {
            return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error("Bad Request")
                    .message(e.getMessage())
                    .build());
        } catch (Exception e) {
            log.error("Erro ao processar registro", e);
            return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error("Bad Request")
                    .message("Erro ao processar registro: " + e.getMessage())
                    .build());
        }
    }

    @Operation(summary = "Login de usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            if (request == null) {
                throw new RegisterValidationException("request", "O corpo da requisição é obrigatório");
            }

            String token = authenticationService.authenticate(request.getCpf());
            return ResponseEntity.ok(AuthResponse.builder().token(token).build());
        } catch (RegisterValidationException e) {
            return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error("Bad Request")
                    .message(e.getMessage())
                    .build());
        } catch (Exception e) {
            log.error("Erro ao processar login", e);
            return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error("Bad Request")
                    .message("Erro ao processar login: " + e.getMessage())
                    .build());
        }
    }
} 
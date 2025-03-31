package com.trustvote.votacao.infrastructure.external;

import com.trustvote.votacao.application.ports.output.ImageProcessor;
import com.trustvote.votacao.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageProcessorImpl implements ImageProcessor {

    private final AppConfig appConfig;

    @Override
    public byte[] processImage(MultipartFile image) {
        try {
            return image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar imagem", e);
        }
    }

    @Override
    public boolean validateImageFormat(MultipartFile image) {
        String contentType = image.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @Override
    public String saveImage(byte[] imageData, String cpf) {
        try {
            String fileName = UUID.randomUUID().toString() + ".jpg";
            Path uploadDir = Paths.get(appConfig.getUploadDir());
            
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path filePath = uploadDir.resolve(fileName);
            Files.write(filePath, imageData);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem", e);
        }
    }
} 
package com.trustvote.votacao.application.ports.output;

import org.springframework.web.multipart.MultipartFile;

public interface ImageProcessor {
    byte[] processImage(MultipartFile image);
    boolean validateImageFormat(MultipartFile image);
    String saveImage(byte[] imageData, String identifier);
} 
package com.trustvote.votacao.domain.exception;

public class RegisterValidationException extends RuntimeException {
    private final String field;
    private final String message;

    public RegisterValidationException(String field, String message) {
        super(message);
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    @Override
    public String getMessage() {
        return message;
    }
} 
package com.trustvote.votacao.domain.shared;

import java.util.Objects;
import java.util.regex.Pattern;

public class Cpf {

    private final String value;

    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");

    public Cpf(String value) {
        if (value == null) {
            throw new IllegalArgumentException("CPF não pode ser nulo");
        }

        String cleaned = value.replaceAll("\\D", "");

        if (!CPF_PATTERN.matcher(cleaned).matches()) {
            throw new IllegalArgumentException("CPF inválido: " + value);
        }

        this.value = cleaned;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cpf)) return false;
        Cpf cpf = (Cpf) o;
        return value.equals(cpf.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

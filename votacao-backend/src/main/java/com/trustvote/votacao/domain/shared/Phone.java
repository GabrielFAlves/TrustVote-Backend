package com.trustvote.votacao.domain.shared;

import java.util.Objects;
import java.util.regex.Pattern;

public class Phone {

    private final String value;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10,11}$"); // ex: 11912345678

    public Phone(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Telefone não pode ser nulo");
        }

        String cleaned = value.replaceAll("\\D", "");

        if (!PHONE_PATTERN.matcher(cleaned).matches()) {
            throw new IllegalArgumentException("Telefone inválido: " + value);
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
        if (!(o instanceof Phone)) return false;
        Phone phone = (Phone) o;
        return value.equals(phone.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

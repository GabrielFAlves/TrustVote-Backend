package com.trustvote.votacao.domain.user;

import com.trustvote.votacao.domain.shared.Cpf;
import com.trustvote.votacao.domain.shared.Email;
import com.trustvote.votacao.domain.shared.Phone;

import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private Cpf cpf;
    private Email email;
    private Phone phone;
    private String password;

    public User() {}

    public User(UUID id, String name, Cpf cpf, Email email, Phone phone, String password) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public Email getEmail() {
        return email;
    }

    public Phone getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCpf(Cpf cpf) {
        this.cpf = cpf;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

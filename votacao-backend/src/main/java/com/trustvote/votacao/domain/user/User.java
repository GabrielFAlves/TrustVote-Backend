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
    private boolean voted;
    private String walletAddress;
    private String privateKey;

    public User() {}

    public User(UUID id, String name, Cpf cpf, Email email, Phone phone, String password, boolean voted, String walletAddress, String privateKey) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.voted = voted;
        this.walletAddress = walletAddress;
        this.privateKey = privateKey;
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

    public boolean getVoted() {
        return voted;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public String getPrivateKey() {
        return privateKey;
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

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
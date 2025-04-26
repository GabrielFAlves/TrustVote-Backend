package com.trustvote.votacao.infrastructure.persistence.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean voted;

    @Column(name = "wallet_address", nullable = false, unique = true)
    private String walletAddress;

    @Column(name = "private_key", nullable = false)
    private String privateKey;

}

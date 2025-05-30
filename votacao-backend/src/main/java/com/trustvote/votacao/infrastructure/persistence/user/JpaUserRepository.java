package com.trustvote.votacao.infrastructure.persistence.user;

import com.trustvote.votacao.infrastructure.persistence.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByCpf(String cpf);
}

package com.trustvote.votacao.infrastructure.persistence;

import com.trustvote.votacao.domain.entities.User;
import com.trustvote.votacao.domain.repositories.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {
    Optional<User> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
} 
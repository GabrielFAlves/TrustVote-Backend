package com.trustvote.votacao.domain.repositories;

import com.trustvote.votacao.domain.entities.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    User save(User user);
} 
package com.trustvote.votacao.domain.user;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByCpf(String cpf);
    void save(User user);
}

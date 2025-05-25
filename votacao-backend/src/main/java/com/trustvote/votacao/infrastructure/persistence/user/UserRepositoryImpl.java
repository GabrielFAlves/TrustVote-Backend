package com.trustvote.votacao.infrastructure.persistence.user;

import com.trustvote.votacao.domain.user.User;
import com.trustvote.votacao.domain.user.UserRepository;
import com.trustvote.votacao.infrastructure.persistence.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("never")
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByCpf(String cpf) {
        return jpaUserRepository.findByCpf(cpf)
                .map(userMapper::toDomain);
    }

    @Override
    public void save(User user) {
        jpaUserRepository.save(userMapper.toEntity(user));
    }
}

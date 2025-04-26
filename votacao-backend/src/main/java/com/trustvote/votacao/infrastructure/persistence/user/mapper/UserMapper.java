package com.trustvote.votacao.infrastructure.persistence.user.mapper;

import com.trustvote.votacao.domain.shared.Cpf;
import com.trustvote.votacao.domain.shared.Email;
import com.trustvote.votacao.domain.shared.Phone;
import com.trustvote.votacao.domain.user.User;
import com.trustvote.votacao.infrastructure.persistence.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                new Cpf(entity.getCpf()),
                new Email(entity.getEmail()),
                new Phone(entity.getPhone()),
                entity.getPassword(),
                entity.isVoted(),
                entity.getWalletAddress(),
                entity.getPrivateKey()
        );
    }

    public UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .cpf(user.getCpf().getValue())
                .email(user.getEmail().getValue())
                .phone(user.getPhone().getValue())
                .password(user.getPassword())
                .walletAddress(user.getWalletAddress())
                .privateKey(user.getPrivateKey())
                .build();
    }

}

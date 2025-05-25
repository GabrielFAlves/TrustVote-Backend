package com.trustvote.votacao.infrastructure.security;

import com.trustvote.votacao.domain.user.UserRepository;
import com.trustvote.votacao.infrastructure.persistence.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
@Profile("never")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        var user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado para CPF: " + cpf));

        return User.builder()
                .username(user.getCpf().getValue())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}

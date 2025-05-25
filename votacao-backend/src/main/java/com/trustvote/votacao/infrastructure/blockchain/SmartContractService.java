package com.trustvote.votacao.infrastructure.blockchain;

import com.trustvote.votacao.application.vote.dto.CandidateBasicDTO;
import com.trustvote.votacao.application.vote.dto.CandidateResultDTO;
import com.trustvote.votacao.infrastructure.persistence.user.JpaUserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SmartContractService {

    private final JpaUserRepository userRepository;

    // TEMPORARIAMENTE DESABILITADO - DADOS FAKE PARA DEBUG
    public List<CandidateBasicDTO> getCandidateList() throws Exception {
        // Retorna dados simulados
        return Arrays.asList(
                new CandidateBasicDTO(0, "Alice Silva", "https://example.com/alice.jpg"),
                new CandidateBasicDTO(1, "Bob Santos", "https://example.com/bob.jpg"),
                new CandidateBasicDTO(2, "Carol Oliveira", "https://example.com/carol.jpg")
        );
    }

    public List<CandidateResultDTO> getCandidateResults() throws Exception {
        // Retorna resultados simulados
        return Arrays.asList(
                new CandidateResultDTO(0, "Alice Silva", "https://example.com/alice.jpg", 15),
                new CandidateResultDTO(1, "Bob Santos", "https://example.com/bob.jpg", 8),
                new CandidateResultDTO(2, "Carol Oliveira", "https://example.com/carol.jpg", 3)
        );
    }

    public void sendVote(String cpf, int candidateId) throws Exception {
        var user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (user.isVoted()) {
            throw new RuntimeException("Usuário já votou");
        }

        // SIMULAÇÃO TEMPORÁRIA - APENAS MARCA COMO VOTADO
        user.setVoted(true);
        userRepository.save(user);

        System.out.println("✅ Voto simulado registrado - CPF: " + cpf + ", Candidato: " + candidateId);
    }
}
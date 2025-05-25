package com.trustvote.votacao.infrastructure.blockchain;

import com.trustvote.votacao.application.vote.dto.CandidateBasicDTO;
import com.trustvote.votacao.application.vote.dto.CandidateResultDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SmartContractService {

    public List<CandidateBasicDTO> getCandidateList() throws Exception {
        return Arrays.asList(
                new CandidateBasicDTO(0, "Alice Silva", "https://example.com/alice.jpg"),
                new CandidateBasicDTO(1, "Bob Santos", "https://example.com/bob.jpg"),
                new CandidateBasicDTO(2, "Carol Oliveira", "https://example.com/carol.jpg")
        );
    }

    public List<CandidateResultDTO> getCandidateResults() throws Exception {
        return Arrays.asList(
                new CandidateResultDTO(0, "Alice Silva", "https://example.com/alice.jpg", 15),
                new CandidateResultDTO(1, "Bob Santos", "https://example.com/bob.jpg", 8),
                new CandidateResultDTO(2, "Carol Oliveira", "https://example.com/carol.jpg", 3)
        );
    }

    public void sendVote(String cpf, int candidateId) throws Exception {
        // SIMULAÇÃO COMPLETA - SEM BANCO DE DADOS
        System.out.println("✅ Voto simulado registrado - CPF: " + cpf + ", Candidato: " + candidateId);

        // Simula validações
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new RuntimeException("CPF não pode ser vazio");
        }

        if (candidateId < 0 || candidateId > 2) {
            throw new RuntimeException("Candidato inválido");
        }

        // Tudo OK - voto "registrado"
        System.out.println("🎉 Voto processado com sucesso!");
    }
}
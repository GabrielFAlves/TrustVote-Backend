package com.trustvote.votacao.application.vote;

import com.trustvote.votacao.application.vote.dto.VoteRequest;
import com.trustvote.votacao.application.vote.dto.CandidateBasicDTO;
import com.trustvote.votacao.application.vote.dto.CandidateResultDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    // REMOVIDO: SmartContractService dependency temporariamente

    @PostMapping("/send")
    public ResponseEntity<?> sendVote(@RequestBody VoteRequest request) {
        try {
            // SIMULAÇÃO TEMPORÁRIA
            System.out.println("✅ Voto simulado - CPF: " + request.getCpf() + ", Candidato: " + request.getCandidateId());
            return ResponseEntity.ok("Voto enviado com sucesso! (simulado)");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao votar: " + e.getMessage());
        }
    }

    @GetMapping("/candidates")
    public ResponseEntity<?> getCandidates() {
        try {
            // DADOS FIXOS TEMPORÁRIOS
            List<CandidateBasicDTO> candidates = Arrays.asList(
                    new CandidateBasicDTO(0, "Alice Silva", "https://example.com/alice.jpg"),
                    new CandidateBasicDTO(1, "Bob Santos", "https://example.com/bob.jpg"),
                    new CandidateBasicDTO(2, "Carol Oliveira", "https://example.com/carol.jpg")
            );
            return ResponseEntity.ok(candidates);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao buscar candidatos: " + e.getMessage());
        }
    }

    @GetMapping("/results")
    public ResponseEntity<?> getResults() {
        try {
            // DADOS FIXOS TEMPORÁRIOS
            List<CandidateResultDTO> results = Arrays.asList(
                    new CandidateResultDTO(0, "Alice Silva", "https://example.com/alice.jpg", 15),
                    new CandidateResultDTO(1, "Bob Santos", "https://example.com/bob.jpg", 8),
                    new CandidateResultDTO(2, "Carol Oliveira", "https://example.com/carol.jpg", 3)
            );
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao buscar resultados: " + e.getMessage());
        }
    }
}
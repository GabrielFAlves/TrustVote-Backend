package com.trustvote.votacao.application.vote;

import com.trustvote.votacao.application.vote.dto.VoteRequest;
import com.trustvote.votacao.infrastructure.blockchain.SmartContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class VoteController {

    private final SmartContractService contractService;

    @PostMapping("/send")
    public ResponseEntity<?> sendVote(@RequestBody VoteRequest request) {
        try {
            contractService.sendVote(request.getCpf(), request.getCandidateId());
            return ResponseEntity.ok("Voto enviado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace(); // <-- Adiciona isso para ver o erro exato no console
            return ResponseEntity.badRequest().body("Erro ao votar: " + e.getMessage());
        }
    }

    @GetMapping("/candidates")
    public ResponseEntity<?> getCandidates() {
        try {
            return ResponseEntity.ok(contractService.getCandidateList());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao buscar candidatos: " + e.getMessage());
        }
    }

    @GetMapping("/results")
    public ResponseEntity<?> getResults() {
        try {
            return ResponseEntity.ok(contractService.getCandidateResults());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao buscar resultados: " + e.getMessage());
        }
    }

}
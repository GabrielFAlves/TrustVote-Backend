package com.trustvote.votacao.application.vote;

import com.trustvote.votacao.application.vote.dto.VoteRequest;
import com.trustvote.votacao.infrastructure.blockchain.SmartContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final SmartContractService contractService;

    @PostMapping("/send")
    public ResponseEntity<?> sendVote(@RequestBody VoteRequest request) {
        try {
            contractService.sendVote(
                    request.getCpf(),
                    request.getWalletAddress(),
                    request.getCandidateId()
            );
            return ResponseEntity.ok("Voto enviado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao votar: " + e.getMessage());
        }
    }

}
package com.trustvote.votacao.application.vote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequest {
    private String cpf;
    private int candidateId;
    private String walletAddress;
}
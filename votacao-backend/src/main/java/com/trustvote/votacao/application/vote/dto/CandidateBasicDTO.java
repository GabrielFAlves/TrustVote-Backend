package com.trustvote.votacao.application.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidateBasicDTO {
    private int id;
    private String name;
    private String photoUrl;
}
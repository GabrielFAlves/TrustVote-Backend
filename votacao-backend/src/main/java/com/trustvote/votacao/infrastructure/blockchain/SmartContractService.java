package com.trustvote.votacao.infrastructure.blockchain;

import com.trustvote.votacao.application.vote.dto.CandidateBasicDTO;
import com.trustvote.votacao.application.vote.dto.CandidateResultDTO;
import com.trustvote.votacao.infrastructure.persistence.user.JpaUserRepository;
import com.trustvote.votacao.infrastructure.blockchain.contracts.Voting;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SmartContractService {

    private final Credentials credentials;
    private final JpaUserRepository userRepository;

    @Value("${blockchain.rpc-url}")
    private String rpc;

    @Value("${CONTRACT_ADDRESS}")
    private String contractAddress;

    public void sendVote(String cpf, int candidateId) throws Exception {
        var user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (user.isVoted()) {
            throw new RuntimeException("Usuário já votou");
        }

        Web3j web3j = Web3j.build(new HttpService(rpc));

        var txManager = new FastRawTransactionManager(
                web3j,
                credentials, // credenciais do backend (admin)
                10, // Chain ID da Optimism Mainnet
                new PollingTransactionReceiptProcessor(web3j, 15_000, 40)
        );

        var gasProvider = new StaticGasProvider(
                BigInteger.valueOf(1_000_000_000L), // 1 Gwei
                BigInteger.valueOf(200_000)
        );

        var contract = Voting.load(contractAddress, web3j, txManager, gasProvider);

        System.out.println("📨 Enviando voto com CPF: " + cpf);

        var receipt = contract.vote(cpf, BigInteger.valueOf(candidateId)).send();

        System.out.println("✅ Voto confirmado. TxHash: " + receipt.getTransactionHash());

        user.setVoted(true);
        userRepository.save(user);
    }

    public List<CandidateBasicDTO> getCandidateList() throws Exception {
        var contract = loadContract();
        List<Voting.Candidate> candidates = contract.getAllCandidates().send();

        List<CandidateBasicDTO> list = new ArrayList<>();
        for (int i = 0; i < candidates.size(); i++) {
            var candidate = candidates.get(i);
            list.add(new CandidateBasicDTO(
                    i,
                    candidate.name,
                    candidate.photoUrl
            ));
        }

        return list;
    }

    public List<CandidateResultDTO> getCandidateResults() throws Exception {
        var contract = loadContract();
        List<Voting.Candidate> candidates = contract.getAllCandidates().send();

        List<CandidateResultDTO> list = new ArrayList<>();
        for (int i = 0; i < candidates.size(); i++) {
            var candidate = candidates.get(i);
            list.add(new CandidateResultDTO(
                    i,
                    candidate.name,
                    candidate.photoUrl,
                    candidate.voteCount.longValue()
            ));
        }

        return list;
    }

    private Voting loadContract() {
        Web3j web3j = Web3j.build(new HttpService(rpc));

        var txManager = new FastRawTransactionManager(
                web3j, credentials, 10,
                new PollingTransactionReceiptProcessor(web3j, 15_000, 40)
        );

        var gasProvider = new StaticGasProvider(
                BigInteger.valueOf(1_000_000_000L), // 1 Gwei
                BigInteger.valueOf(200_000)
        );

        return Voting.load(contractAddress, web3j, txManager, gasProvider);
    }

}

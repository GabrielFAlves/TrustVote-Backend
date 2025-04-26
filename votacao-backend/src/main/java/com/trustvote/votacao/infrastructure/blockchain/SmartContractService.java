package com.trustvote.votacao.infrastructure.blockchain;

import com.trustvote.votacao.infrastructure.persistence.user.JpaUserRepository;
import com.trustvote.votacao.infrastructure.blockchain.contracts.Voting;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class SmartContractService {

    private final Credentials credentials;
    private final JpaUserRepository userRepository;

    @Value("${blockchain.rpc-url}")
    private String rpc;

    @Value("${CONTRACT_ADDRESS}")
    private String contractAddress;

    public void sendVote(String cpf, String walletAddress, int candidateId) throws Exception {
        var user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (user.isVoted()) {
            throw new RuntimeException("Usuário já votou");
        }

        Web3j web3j = Web3j.build(new HttpService(rpc));

        // Use o chainId da Amoy (Polygon testnet): 80002
        var txManager = new RawTransactionManager(web3j, credentials, 80002);

        // Usar gasPrice manualmente (mínimo 25 Gwei), e gasLimit suficiente (300000)
        BigInteger gasPrice = BigInteger.valueOf(25_000_000_000L); // 25 Gwei
        BigInteger gasLimit = BigInteger.valueOf(300_000);
        var gasProvider = new StaticGasProvider(gasPrice, gasLimit);

        var contract = Voting.load(contractAddress, web3j, txManager, gasProvider);

        // 1. Criar o hash da mensagem
        byte[] message = Numeric.hexStringToByteArray(walletAddress);
        byte[] messageHash = Hash.sha3(message);

        // 2. Formatar como "Ethereum Signed Message"
        String prefix = "\u0019Ethereum Signed Message:\n32";
        byte[] prefixBytes = prefix.getBytes();
        byte[] ethMessage = new byte[prefixBytes.length + messageHash.length];
        System.arraycopy(prefixBytes, 0, ethMessage, 0, prefixBytes.length);
        System.arraycopy(messageHash, 0, ethMessage, prefixBytes.length, messageHash.length);
        byte[] ethSignedMessageHash = Hash.sha3(ethMessage);

        // 3. Assinar com a private key do backend
        Sign.SignatureData sig = Sign.signMessage(ethSignedMessageHash, credentials.getEcKeyPair(), false);
        byte[] signature = new byte[65];
        System.arraycopy(sig.getR(), 0, signature, 0, 32);
        System.arraycopy(sig.getS(), 0, signature, 32, 32);
        signature[64] = sig.getV()[0];

        // 4. Enviar a transação
        contract.vote(walletAddress, BigInteger.valueOf(candidateId), signature).send();

        // 5. Marcar como votado
        user.setVoted(true);
        userRepository.save(user);
    }
}

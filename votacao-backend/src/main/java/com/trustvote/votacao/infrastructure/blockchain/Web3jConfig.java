package com.trustvote.votacao.infrastructure.blockchain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;

@Configuration
public class Web3jConfig {

    @Value("${blockchain.private-key}")
    private String privateKey;

    @Bean
    public Credentials blockchainCredentials() {
        Credentials credentials = Credentials.create(privateKey);
        System.out.println("🔐 BACKEND_SIGNER: " + credentials.getAddress());
        return credentials;
    }
}


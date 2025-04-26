package com.trustvote.votacao.tools;

import org.web3j.crypto.Credentials;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PrintBackendSignerFromProperties {

    public static void main(String[] args) {
        try (InputStream input = PrintBackendSignerFromProperties.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                System.out.println("Arquivo application.properties não encontrado.");
                return;
            }

            Properties prop = new Properties();
            prop.load(input);

            String privateKey = prop.getProperty("blockchain.private-key");

            if (privateKey == null || privateKey.isBlank()) {
                System.out.println("Chave privada não encontrada em application.properties");
                return;
            }

            Credentials credentials = Credentials.create(privateKey);
            System.out.println("BACKEND_SIGNER: " + credentials.getAddress());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

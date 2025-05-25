package com.trustvote.votacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {
		SecurityAutoConfiguration.class,
		DataSourceAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class
})
public class VotacaoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotacaoBackendApplication.class, args);
	}
}
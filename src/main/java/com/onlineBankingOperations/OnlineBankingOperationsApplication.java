package com.onlineBankingOperations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableScheduling
public class OnlineBankingOperationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineBankingOperationsApplication.class, args);
	}

}

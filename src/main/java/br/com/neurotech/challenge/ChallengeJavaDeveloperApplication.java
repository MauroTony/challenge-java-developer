package br.com.neurotech.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Challenge Java Developer", version = "1.0", description = "Challenge Java Developer to Neurotech"))
public class ChallengeJavaDeveloperApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeJavaDeveloperApplication.class, args);
	}

}

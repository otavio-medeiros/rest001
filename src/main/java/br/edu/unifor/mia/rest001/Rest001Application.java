package br.edu.unifor.mia.rest001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Rest001Application {

	public static void main(String[] args) {
		SpringApplication.run(Rest001Application.class, args);
	}

}
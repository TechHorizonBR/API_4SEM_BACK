package br.fatec.bd4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TechHorizonApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechHorizonApplication.class, args);
	}

}

package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoDataApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicacion arrancada ...");
	}

	@Bean
	CommandLineRunner demosDatos(EjemplosDatos demos) {
		return args -> {
			// demos.actores();
//			demos.consultas();
			try {
				demos.transaccion();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		};
	}
}

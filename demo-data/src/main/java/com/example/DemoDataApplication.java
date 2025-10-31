package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.application.services.MessagingService;

import jakarta.annotation.PreDestroy;

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
//			demos.actores();
//			 demos.consultas();
//			try {
//				demos.transaccion();
//			} catch (Exception e) {
//				System.err.println(e.getMessage());
//			}
//			demos.proyecciones();
//			demos.peliculas();
			demos.categorias();
		};
	}

//	@Autowired
//	MessagingService mensajeria;
//	
//	@Bean
//	CommandLineRunner demosCorreos() {
//		return args -> {
//			mensajeria.sendEmailAsync("pgrillo@example.com", "Aplicacion Init", "La aplicacion se ha iniciado");
//			mensajeria.sendWelcomeEmailAsync("pgrillo@example.com", "Pepito Grillo");
//		};
//	}
//	
//	@PreDestroy
//	void despidete() {
//		var body = """
//<!DOCTYPE html>
//<html lang="es">
//<head>
//    <meta charset="UTF-8">
//    <meta name="viewport" content="width=device-width, initial-scale=1.0">
//    <title>Servicio</title>
//</head>
//<body>
//    <h1>%s</h1>
//    <p>%s</p>
//</body>
//</html>
//""".formatted("Aplicacion Close", "La aplicacion se ha cerrado");
//		mensajeria.sendMimeEmail("pgrillo@example.com", "Aplicacion Close", body, true);
//	}

}

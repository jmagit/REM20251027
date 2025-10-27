package com.example;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.ioc.ClaseNoComponente;
import com.example.ioc.NotificationService;
import com.example.ioc.Rango;
import com.example.ioc.anotaciones.Remoto;
import com.example.ioc.contratos.Servicio;
import com.example.ioc.contratos.ServicioCadenas;
import com.example.ioc.notificaciones.Sender;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada ...");
	}
	
	@Autowired
	NotificationService notify;
	@Autowired(required = false) ClaseNoComponente noComponente;
	
//	@Bean
	CommandLineRunner ejemplosIoC(ServicioCadenas srv) {
		return arg -> {
			// ServicioCadenas srv = servicioCadenasMock;
			notify.add("Desde el bean ...");
			notify.add(srv.getClass().getSimpleName());
			//ServicioCadenas srv = new ServicioCadenasImpl(new RepositorioCadenasImpl(new ConfiguracionImpl(notify), notify), notify);
			srv.get().forEach(notify::add);
			srv.modify("modifico");
			if(noComponente != null)
			noComponente.saluda();
			// ...
			System.out.println("==============================>");
			notify.getListado().forEach(System.out::println);
			notify.clear();
			System.out.println("<==============================");
		};
	}
	
//	@Bean
	CommandLineRunner porNombre(Sender correo, Sender fichero, Sender twittea) {
		return arg -> {
			correo.send("Hola mundo");
			fichero.send("Hola mundo");
			twittea.send("Hola mundo");
		};
	}

//	@Bean
	CommandLineRunner cualificados(@Qualifier("local") Sender local, @Remoto Sender remoto, Sender primario) {
		return arg -> {
			primario.send("Hola por defecto");
			local.send("Hola local");
			remoto.send("Hola remoto");
		};
	}

//	@Bean
	CommandLineRunner multiple(List<Sender> senders, Map<String, Sender> mapa, List<Servicio> servicios) {
		return arg -> {
			senders.forEach(s -> s.send(s.getClass().getCanonicalName()));
			mapa.forEach((k, v) -> System.out.println("%s -> %s".formatted(k, v.getClass().getCanonicalName())));
			servicios.forEach(s -> System.out.println(s.getClass().getCanonicalName()));
		};
	}


	@Bean
	CommandLineRunner valores(@Value("${mi.valor:Sin valor}") String miValor, Rango rango, @Value("${spring.datasource.url}") String db) {
		return arg -> {
			System.out.println(miValor);
			System.out.println(rango);
			System.out.println(db);
		};
	}


}

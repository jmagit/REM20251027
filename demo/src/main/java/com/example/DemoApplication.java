package com.example;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.annotation.AsyncAnnotationAdvisor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.aop.AuthenticationService;
import com.example.aop.StrictNullChecksAspect;
import com.example.aop.introductions.Visible;
import com.example.aop.introductions.VisibleAspect;
import com.example.ioc.ClaseNoComponente;
import com.example.ioc.Dummy;
import com.example.ioc.GenericoEvent;
import com.example.ioc.NotificationService;
import com.example.ioc.Rango;
import com.example.ioc.anotaciones.Remoto;
import com.example.ioc.contratos.Configuracion;
import com.example.ioc.contratos.Servicio;
import com.example.ioc.contratos.ServicioCadenas;
import com.example.ioc.notificaciones.ConstructorConValores;
import com.example.ioc.notificaciones.Sender;

@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
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
	@Autowired(required = false)
	ClaseNoComponente noComponente;

//	@Bean
	CommandLineRunner ejemplosIoC(ServicioCadenas srv) {
		return arg -> {
			// ServicioCadenas srv = servicioCadenasMock;
			notify.add("Desde el bean ...");
			notify.add(srv.getClass().getSimpleName());
			// ServicioCadenas srv = new ServicioCadenasImpl(new RepositorioCadenasImpl(new
			// ConfiguracionImpl(notify), notify), notify);
			srv.get().forEach(notify::add);
			srv.modify("modifico");
			if (noComponente != null)
				noComponente.saluda();
			// ...
//			System.out.println("==============================>");
//			notify.getListado().forEach(System.out::println);
//			notify.clear();
//			System.out.println("<==============================");
		};
	}

//	@Bean
	CommandLineRunner beansPorNombre(Sender correo, Sender fichero, Sender twittea) {
		return arg -> {
			correo.send("Hola mundo");
			fichero.send("Hola mundo");
			twittea.send("Hola mundo");
		};
	}

//	@Bean
	CommandLineRunner beansCualificados(@Qualifier("local") Sender local, @Remoto Sender remoto, Sender primario) {
		return arg -> {
			primario.send("Hola por defecto");
			local.send("Hola local");
			remoto.send("Hola remoto");
		};
	}

//	@Bean
	CommandLineRunner multiplesBeans(List<Sender> senders, Map<String, Sender> mapa, List<Servicio> servicios) {
		return arg -> {
			senders.forEach(s -> s.send(s.getClass().getCanonicalName()));
			mapa.forEach((k, v) -> System.out.println("%s -> %s".formatted(k, v.getClass().getCanonicalName())));
			servicios.forEach(s -> System.out.println(s.getClass().getCanonicalName()));
		};
	}

//	@Bean
	CommandLineRunner inyectaValores(@Value("${mi.valor:Sin valor}") String miValor, Rango rango,
			@Value("${spring.datasource.url}") String db) {
		return arg -> {
			System.out.println(miValor);
			System.out.println(rango);
			System.out.println(db);
		};
	}

//	@Bean
	CommandLineRunner inyectaPrimitivos(ConstructorConValores demo) {
		return arg -> {
			System.out.println("inyectaPrimitivos ==============================>");
			notify.getListado().forEach(System.out::println);
			notify.clear();
			System.out.println("<==============================");
		};
	}

//	@Bean
	CommandLineRunner configuracionEnXML() {
		return arg -> {
			try (var contexto = new FileSystemXmlApplicationContext("applicationContext.xml")) {
				var notify = contexto.getBean(NotificationService.class);
				System.out.println("configuracionEnXML ===================>");
				var srv = (ServicioCadenas) contexto.getBean("servicioCadenas");
				System.out.println(srv.getClass().getName());
				contexto.getBean(NotificationService.class).getListado().forEach(System.out::println);
				System.out.println("===================>");
				srv.get().forEach(notify::add);
				srv.add("Hola mundo");
				notify.add(srv.get(1));
				srv.modify("modificado");
				System.out.println("===================>");
				notify.getListado().forEach(System.out::println);
				notify.clear();
				System.out.println("<===================");
				((Sender) contexto.getBean("sender")).send("Hola mundo");
			}
		};
	}

//	@EventListener
	void eventHandler(GenericoEvent ev) {
		System.err.println("Evento recibido de %s: %s".formatted(ev.origen(), ev.carga()));
	}

	@Scheduled(initialDelay = 5, fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
	void proceso() {
		// System.out.println("Han pasado 5 segundos");
		if (notify.hasMessages()) {
			System.out.println("Scheduled ==============================>");
			notify.getListado().forEach(System.out::println);
			notify.clear();
			System.out.println("<==============================");
		}
	}
	
//	@Bean
	CommandLineRunner asincrono(Dummy dummy) {
		return arg -> {
			var obj = dummy; // new Dummy();
			System.err.println(obj.getClass().getCanonicalName());
			obj.ejecutarAutoInvocado(1);
			obj.ejecutarAutoInvocado(2);
//			obj.ejecutarTareaSimpleAsync(1);
//			obj.ejecutarTareaSimpleAsync(2);
			obj.calcularResultadoAsync(10, 20, 30, 40, 50).thenAccept(result -> notify.add(result));
			obj.calcularResultadoAsync(1, 2, 3).thenAccept(result -> notify.add(result));
			obj.calcularResultadoAsync().thenAccept(result -> notify.add(result));
			System.err.println("Termino de mandar hacer las cosas");
		};
	}

//	@Bean
	CommandLineRunner aop(Configuracion config, Dummy dummy) {
		return args -> {
			notify.clear();
			try {
				int v = config.getNext();
				notify.add("Mensaje " + v + config.getNext());
				notify.getListado().forEach(System.out::println);
				config.config();
			} catch (Exception e) {
				System.err.println("Desde el consejo: " + e.getMessage());
			}
			try {
				dummy.setControlado(null);
				System.out.println("No llega la excepcion");
				System.out.println("Controlado: " + dummy.getControlado().get());
				dummy.setControlado("minuscula");
				System.out.println("Controlado: " + dummy.getControlado().get());
			} catch (Exception e) {
				System.err.println("Error controlado: " + e.getMessage());
			}
			if(dummy instanceof Visible v) {
				System.out.println(v.isVisible() ? "Es visible" : "Es invisible");
				v.mostrar();
				System.out.println(v.isVisible() ? "Es visible" : "Es invisible");
				v.ocultar();
				System.out.println(v.isVisible() ? "Es visible" : "Es invisible");
			} else {
				System.err.println("No implementa Visible");
			}
		};
	}

//	@Bean
	CommandLineRunner interceptores(ServicioCadenas srv, AuthenticationService auth) {
		return arg -> {
			System.out.println(srv.getClass().getSimpleName());
			srv.get().forEach(notify::add);
			auth.login();
			try {
				srv.add("añado");
			} catch (Exception e) {
				System.err.println("añado %s -> %s".formatted(e.getClass().getSimpleName(), e.getMessage()));
			}
			try {
				srv.modify("modifico");
			} catch (Exception e) {
				System.err.println("modifico %s -> %s".formatted(e.getClass().getSimpleName(), e.getMessage()));
			}
		};
	}
	@Bean
	CommandLineRunner creacionManual(ApplicationContext ctx) {
		return args -> {
			Dummy dummy = new Dummy();
//			AspectJProxyFactory factory = new AspectJProxyFactory(dummy);
//			factory.addAspect(VisibleAspect.class);
//			factory.addAspect(StrictNullChecksAspect.class);
////			factory.addAspect(AsyncAnnotationAdvisor.class);
//			dummy = factory.getProxy();

			dummy = ctx.getBean(Dummy.class);
			
			dummy.ejecutarTareaSimpleAsync(10);
			if(dummy instanceof Visible v) {
				System.out.println(v.isVisible() ? "Es visible" : "Es invisible");
				v.mostrar();
				System.out.println(v.isVisible() ? "Es visible" : "Es invisible");
				v.ocultar();
				System.out.println(v.isVisible() ? "Es visible" : "Es invisible");
			} else {
				System.err.println("No implementa Visible");
			}
			try {
				dummy.setDescontrolado(null);
				System.out.println("Deja poner nulos");
			} catch (Exception e) {
				System.err.println("Pon nulo %s -> %s".formatted(e.getClass().getSimpleName(), e.getMessage()));
			}

		};
	}

}

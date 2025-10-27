package com.example.ioc.implementaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.ioc.NotificationService;
import com.example.ioc.anotaciones.RepositoryMock;
import com.example.ioc.contratos.RepositorioCadenas;

import jakarta.annotation.PostConstruct;

@RepositoryMock
//@Repository
//@Primary
public class RepositorioCadenasMock implements RepositorioCadenas {
	@Autowired
	private NotificationService notify;
	
	public RepositorioCadenasMock() {
//		notify.add(getClass().getSimpleName() + " Constructor");
	}
	@PostConstruct
	private void init() {
		notify.add(getClass().getSimpleName() + " Constructor");
	}

	@Override
	public String load() {
		return "Simulación de una cadena leida";
	}


	@Override
	public void save(String item) {
		notify.add("Simulo que guardo los datos '%s' con %s".formatted(item, getClass().getSimpleName()));
		doEvent("Han ejecutado el guardar.");
		
	}	
	
	@Autowired 
	private ApplicationEventPublisher publisher;
	public void setPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
	protected void doEvent(@NonNull String event) { 
		publisher.publishEvent(event); 
	}
}
package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.contracts.domain.repositories.ActorsRepository;
import com.example.domain.entities.Actor;

@Component
public class EjemplosDatos {
	@Autowired
	ActorsRepository daoActors;
	
	public void actores() {
		var actor = new Actor("Pepito", "Grillo");
		var id = daoActors.save(actor).getId();
		var leido = daoActors.findById(id);
		if(leido.isPresent()) {
			actor = leido.get();
			System.out.println(actor);
			actor.setFirstName(actor.getFirstName().toUpperCase());
			daoActors.save(actor);
		} else {
			System.err.println("No encontrado");
		}
		daoActors.findAll().forEach(System.out::println);
		daoActors.deleteById(id);
		daoActors.findAll().forEach(System.out::println);
	}
}

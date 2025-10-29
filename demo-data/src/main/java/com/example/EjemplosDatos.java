package com.example;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
	//@Transactional
	public void consultas() {
//		daoActors.findTop5ByFirstNameStartingWithIgnoreCaseOrderByLastNameDesc("p").forEach(System.out::println);
//		daoActors.findTop5ByFirstNameStartingWithIgnoreCase("p", Sort.by("FirstName").descending()).forEach(System.out::println);
//		daoActors.findByIdGreaterThanEqual(197).forEach(System.out::println);
//		daoActors.findNovedadesJPQL(197).forEach(System.out::println);
//		daoActors.findNovedadesSQL(197).forEach(System.out::println);
//		daoActors.findAll((root, query, builder) -> builder.greaterThanOrEqualTo(root.get("id"), 197)).forEach(System.out::println);
//		daoActors.findAll((root, query, builder) -> builder.lessThan(root.get("id"), 5)).forEach(System.out::println);
//		daoActors.findParcial(197).forEach(System.out::println);
//		daoActors.findNovedadesJoin(LocalDateTime.of(2021,  1, 1, 0, 0, 0)).forEach(System.out::println);
		daoActors.findNovedadesJoin(LocalDateTime.of(2021,  1, 1, 0, 0, 0)).forEach(item -> {
			System.out.println(item);
			item.getFilmActors().forEach(p -> System.out.println("\t" + p.getId().getFilmId()));
		});
	}
}

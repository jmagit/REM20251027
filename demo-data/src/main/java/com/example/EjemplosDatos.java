package com.example;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.contracts.domain.repositories.ActorsRepository;
import com.example.contracts.domain.repositories.CategoriesRepository;
import com.example.contracts.domain.repositories.PeliculasRepository;
import com.example.domain.entities.Actor;
import com.example.domain.entities.Category;
import com.example.domain.entities.model.ActorDTO;
import com.example.domain.entities.model.ActorShort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Component
public class EjemplosDatos {
	@Autowired
	ActorsRepository daoActors;
	@Autowired
	CategoriesRepository daoCategories;
	@Autowired
	PeliculasRepository daoPelis;
	
	public void actores() {
//		var actor = new Actor("Pepito", "Grillo");
//		var id = daoActors.save(actor).getId();
//		var leido = daoActors.findById(id);
//		if(leido.isPresent()) {
//			actor = leido.get();
//			System.out.println(actor);
//			actor.setFirstName(actor.getFirstName().toUpperCase());
//			daoActors.save(actor);
//		} else {
//			System.err.println("No encontrado");
//		}
//		daoActors.findAll().forEach(System.out::println);
//		daoActors.deleteById(id);
//		daoActors.findAll().forEach(System.out::println);
//		var actor = new Actor(null, "Ñ U");
//		if(actor.isInvalid()) {
//			System.err.println(actor.getErrorsMessage());
//		} else {
//			daoActors.save(actor);
//		}
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
//		daoActors.findNovedadesJoin(LocalDateTime.of(2021,  1, 1, 0, 0, 0)).forEach(item -> {
//			System.out.println(item);
//			item.getFilmActors().forEach(p -> System.out.println("\t" + p.getId().getFilmId()));
//		});
		daoActors.findNovedadesJPQL().forEach(item -> {
			System.out.println(item);
			item.getFilmActors().forEach(p -> System.out.println("\t" + p.getFilm().getTitle()));
		});
	}
	
	@Transactional(rollbackFor = {Exception.class})
	public void transaccion() throws Exception {
		daoActors.save(new Actor("Pepito", "Grillo"));
//		if(true)
//		throw new Exception("Forzado");
		daoCategories.save(new Category(1, "kk"));
		daoActors.save(new Actor("Carmelo", "Coton"));
		daoActors.deleteById(1);
	}
	
	public void proyecciones() {
//		daoActors.findByIdGreaterThanEqual(197).forEach(item -> System.out.println(ActorDTO.from(item)));
//		daoActors.getByIdGreaterThanEqual(197).forEach(System.out::println);
//		daoActors.queryByIdGreaterThanEqual(197).forEach(item -> {
//			System.out.println(item.getId() + " -> " + item.getNombre());
//		});
//		daoActors.searchByIdGreaterThanEqual(197, ActorDTO.class).forEach(System.out::println);
//		daoActors.searchByIdGreaterThanEqual(197, ActorShort.class).forEach(item -> {
//			System.out.println(item.getId() + " -> " + item.getNombre());
//		});
		daoActors.queryNovedadesJPQL(197).forEach(item -> {
			System.out.println(item.getId() + " -> " + item.getNombre());
		});
	}
	public static interface Pelis {
		int getId();
		String getTitle();
		@Value("#{target.language.name}")
		String getIdioma();
	}
	public static record PeliDto(int id, String title, @Value("#{target.language.name}") String idioma) {}

	public void peliculas() {
//		daoActors.findByIdGreaterThanEqual(197).forEach(item -> System.out.println(ActorDTO.from(item)));
//		daoActors.getByIdGreaterThanEqual(197).forEach(System.out::println);
//		daoActors.queryByIdGreaterThanEqual(197).forEach(item -> {
//			System.out.println(item.getId() + " -> " + item.getNombre());
//		});
//		daoActors.searchByIdGreaterThanEqual(197, ActorDTO.class).forEach(System.out::println);
//		daoActors.searchByIdGreaterThanEqual(197, ActorShort.class).forEach(item -> {
//			System.out.println(item.getId() + " -> " + item.getNombre());
//		});
//		daoPelis.queryAll(Pelis.class).forEach(item -> {
//			System.out.println("%d -> %s (%s)".formatted(item.getId(),item.getTitle(), item.getIdioma()));
//		});
//		daoPelis.searchAll(PeliDto.class).forEach(item -> {
//			System.out.println("%d -> %s (%s)".formatted(item.id(),item.title(), item.idioma()));
//		});
		daoPelis.findById(1);
	}
	@Autowired
	ObjectMapper mapper;
	
	public void categorias() {
		daoCategories.findAll().forEach(item -> {
			try {
				System.out.println(mapper.writeValueAsString(item));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});
		XmlMapper mapperXML = new XmlMapper();
		daoCategories.findAll().forEach(item -> {
			try {
				System.out.println(mapperXML.writeValueAsString(item));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});
		
	}
}

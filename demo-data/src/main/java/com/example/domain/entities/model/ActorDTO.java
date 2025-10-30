package com.example.domain.entities.model;

import org.springframework.data.annotation.PersistenceCreator;

import com.example.domain.entities.Actor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class ActorDTO {
	private int id;
	private String nombre;
	private String apellidos;
	
	@PersistenceCreator
	public ActorDTO(int id, String firstName, String lastName) {
		super();
		this.id = id;
		this.nombre = firstName;
		this.apellidos = lastName;
	}

	public static ActorDTO from(Actor source) {
		return new ActorDTO(source.getId(), source.getFirstName(), source.getLastName());
	}

	public static Actor from(ActorDTO source) {
		return new Actor(source.getId(), source.getNombre(), source.getApellidos());
	}
}

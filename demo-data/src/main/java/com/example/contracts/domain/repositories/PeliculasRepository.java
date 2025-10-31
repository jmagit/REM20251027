package com.example.contracts.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.core.contracts.domain.repositories.ProjectionsAndSpecificationJpaRepository;
import com.example.core.contracts.domain.repositories.RepositoryWithProjections;
import com.example.domain.entities.Film;

@RepositoryRestResource(path="peliculas", itemResourceRel="pelicula", collectionResourceRel="peliculas")
public interface PeliculasRepository extends JpaRepository<Film, Integer> {

	@RestResource(exported = false)
	@Query("from Film f where f.id <= 5")
	<T> List<T> queryAll(Class<T> type);
	@RestResource(exported = false)
	@Query("select f.id, f.title, f.language.name from Film f where f.id <= 5")
	<T> List<T> searchAll(Class<T> type);
}

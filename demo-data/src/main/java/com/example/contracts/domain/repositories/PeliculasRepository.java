package com.example.contracts.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.core.contracts.domain.repositories.ProjectionsAndSpecificationJpaRepository;
import com.example.core.contracts.domain.repositories.RepositoryWithProjections;
import com.example.domain.entities.Film;

public interface PeliculasRepository extends ProjectionsAndSpecificationJpaRepository<Film, Integer> {

	@Query("from Film f where f.id <= 5")
	<T> List<T> queryAll(Class<T> type);
	@Query("select f.id, f.title, f.language.name from Film f where f.id <= 5")
	<T> List<T> searchAll(Class<T> type);
}

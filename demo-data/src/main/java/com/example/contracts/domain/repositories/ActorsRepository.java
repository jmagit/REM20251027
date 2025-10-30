package com.example.contracts.domain.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Meta;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import com.example.domain.entities.Actor;

public interface ActorsRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor> {

	List<Actor> findTop5ByFirstNameStartingWithIgnoreCaseOrderByLastNameDesc(String prefijo);
	List<Actor> findTop5ByFirstNameStartingWithIgnoreCase(String prefijo, Sort orderBy);
	
	@Meta(comment = "con DSL")
	List<Actor> findByIdGreaterThanEqual(int id);
	@Meta(comment = "con JPQL")
	@Query("from Actor a where a.id >= ?1")
	@EntityGraph(attributePaths = {"filmActors.film"})
	List<Actor> findNovedadesJPQL(int id);
	@Meta(comment = "con SQL")
	@NativeQuery("select * from actor a where actor_id >= :id")
	List<Actor> findNovedadesSQL(int id);
	@Meta(comment = "con SQL")
	@NativeQuery("select first_name, last_name from actor a where actor_id >= :id")
	List<Map<String, Object>> findParcial(int id);
	@Meta(comment = "con JOIN")
	@Query("from Actor a inner join a.filmActors f where f.lastUpdate >= ?1")
	List<Actor> findNovedadesJoin(LocalDateTime fecha);
	
	@Query("from Actor a where a.id > ?${actor.min.id}")
	@EntityGraph(attributePaths = {"filmActors.film"})
	List<Actor> findNovedadesJPQL();
	
}

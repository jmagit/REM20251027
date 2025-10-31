package com.example.contracts.domain.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.domain.entities.Category;

@RepositoryRestResource(path="categorias", itemResourceRel="categoria", collectionResourceRel="categorias")
public interface CategoriesRepository extends CrudRepository<Category, Integer> {
	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

}

package com.example.contracts.domain.services;

import com.example.core.contracts.domain.services.PagingAndSortingDomainService;
import com.example.domain.entities.Actor;

public interface ActorsService extends PagingAndSortingDomainService<Actor, Integer> {
	void repartePalmares(String elPalmares);
}

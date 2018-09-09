package com.food.recipe.repositories.lkp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.lkp.Period;

@RepositoryRestResource(collectionResourceRel = "period", path = "period")
public interface PeriodRepo extends JpaRepository<Period, Integer> {

}

package com.food.recipe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.DishPeriod;

@RepositoryRestResource(collectionResourceRel = "dishperiod", path = "dishperiod")
public interface DishPeriodRepo extends JpaRepository<DishPeriod, Integer> {

}

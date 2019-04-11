package com.food.recipe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.DishOptimalTime;

@RepositoryRestResource(collectionResourceRel = "dishperiod", path = "dishperiod")
public interface DishOptimalTimeRepo extends JpaRepository<DishOptimalTime, Integer> {

}

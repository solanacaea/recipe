package com.food.recipe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.DishOptimalStage;

@RepositoryRestResource(collectionResourceRel = "dishstage", path = "dishstage")
public interface DishOptimalStageRepo extends JpaRepository<DishOptimalStage, Integer> {

}

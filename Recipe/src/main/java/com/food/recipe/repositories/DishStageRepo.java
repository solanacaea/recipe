package com.food.recipe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.DishStage;

@RepositoryRestResource(collectionResourceRel = "dishstage", path = "dishstage")
public interface DishStageRepo extends JpaRepository<DishStage, Integer> {

}

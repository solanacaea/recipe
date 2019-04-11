package com.food.recipe.repositories.lkp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.lkp.OptimalStage;

@RepositoryRestResource(collectionResourceRel = "stage", path = "stage")
public interface OptimalStageRepo extends JpaRepository<OptimalStage, Integer> {

}

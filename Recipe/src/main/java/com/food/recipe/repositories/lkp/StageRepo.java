package com.food.recipe.repositories.lkp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.lkp.Stage;

@RepositoryRestResource(collectionResourceRel = "stage", path = "stage")
public interface StageRepo extends JpaRepository<Stage, Integer> {

}

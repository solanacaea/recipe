package com.food.recipe.repositories.lkp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.lkp.Function;

@RepositoryRestResource(collectionResourceRel = "function", path = "function")
public interface FunctionRepo extends JpaRepository<Function, Integer> {

}

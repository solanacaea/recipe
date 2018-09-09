package com.food.recipe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.DishFunction;

@RepositoryRestResource(collectionResourceRel = "dishfunction", path = "dishfunction")
public interface DishFunctionRepo extends JpaRepository<DishFunction, Integer> {

}

package com.food.recipe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.DishType;

@RepositoryRestResource(collectionResourceRel = "dishtype", path = "dishtype")
public interface DishTypeRepo extends JpaRepository<DishType, Integer> {

}

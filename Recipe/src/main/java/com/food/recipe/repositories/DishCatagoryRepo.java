package com.food.recipe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.DishCatagory;

@RepositoryRestResource(collectionResourceRel = "dishcatagory", path = "dishcatagory")
public interface DishCatagoryRepo extends JpaRepository<DishCatagory, Integer> {

}

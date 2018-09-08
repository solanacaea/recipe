package com.food.recipe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.Dish;

@RepositoryRestResource(collectionResourceRel = "dish", path = "dish")
public interface DishRepo extends JpaRepository<Dish, Integer> {

}

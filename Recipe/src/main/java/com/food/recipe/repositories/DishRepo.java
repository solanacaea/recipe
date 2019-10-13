package com.food.recipe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.food.recipe.entries.Dish;

@RepositoryRestResource(collectionResourceRel = "dish", path = "dish")
@Transactional
public interface DishRepo extends JpaRepository<Dish, Integer> {
	
	@Query("from Dish order by updatedDate desc")
	public List<Dish> findAllDishes();
}

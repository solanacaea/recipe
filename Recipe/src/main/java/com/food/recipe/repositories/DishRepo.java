package com.food.recipe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.Dish;

@RepositoryRestResource(collectionResourceRel = "dish", path = "dish")
public interface DishRepo extends JpaRepository<Dish, Integer> {
	
//	@Query("from Dish where category = :category and efficacy in (:efficacy) and optimalTime")
//	public List<Dish> findByWeekAndCategory(@Param("week") int week, 
//			@Param("category") String category,
//			@Param("efficacy") String[] efficacy);
}

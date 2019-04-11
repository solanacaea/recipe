package com.food.recipe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.Dish;

@RepositoryRestResource(collectionResourceRel = "dish", path = "dish")
public interface DishRepo extends JpaRepository<Dish, Integer> {

	@Query(value = "select distinct d from Dish d " + 
			"inner join DishCategory c on c.dish.dishId = d.dishId " + 
			"inner join DishEfficacy f on f.dish.dishId = d.dishId " + 
			"inner join DishOptimalTime p on p.dish.dishId = d.dishId " + 
			"inner join DishOptimalStage s on s.dish.dishId = d.dishId " + 
			"inner join DishProperty t on t.dish.dishId = d.dishId " + 
			"where c.category.id in (?1) " + 
			"and f.efficacy.id in (?2) " + 
			"and p.optimalTime.id in (?3) " + 
			"and s.optimalStage.id in (?4) " + 
			"and t.property.id in (?5) ")
	List<Dish> findByParam(
			@Param("c") List<Integer> cate, 
			@Param("f") List<Integer> func, 
			@Param("p") List<Integer> peri, 
			@Param("s") List<Integer> stag, 
			@Param("s") List<Integer> type);
	
	@Query(value = "select distinct d from Dish d " + 
			"inner join DishCategory c on c.dish.dishId = d.dishId " + 
			"inner join DishEfficacy f on f.dish.dishId = d.dishId " + 
			"inner join DishOptimalTime p on p.dish.dishId = d.dishId " + 
			"inner join DishOptimalStage s on s.dish.dishId = d.dishId " + 
			"inner join DishProperty t on t.dish.dishId = d.dishId " + 
			"where c.category.id in (?1) " + 
			"and f.efficacy.id in (?2) " + 
			"and p.optimalTime.id in (?3) " + 
			"and s.optimalStage.id in (?4) " + 
			"and t.property.id in (?5) ")
	List<Dish> findByParam(
			@Param("c") Integer cate, 
			@Param("f") List<Integer> func, 
			@Param("p") Integer peri, 
			@Param("s") Integer stag, 
			@Param("s") List<Integer> type);
}

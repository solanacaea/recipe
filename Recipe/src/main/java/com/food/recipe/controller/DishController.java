package com.food.recipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.entries.Dish;
import com.food.recipe.repositories.DishRepo;

@RestController
@RequestMapping("/dish")
public class DishController {

	@Autowired
	private DishRepo repo;
	
	@RequestMapping("/getall")
	@GetMapping
	public List<Dish> getAll() {
		return repo.findAll();
	}
	

	@RequestMapping("/recipe")
	@PostMapping
	public List<Dish> getCustomRecipe(@RequestBody Dish b) {
		return null;
	}
	
	@RequestMapping("/save")
	@PostMapping
	public void save(@RequestBody Dish d) {
		repo.saveAndFlush(d);
	}
	
	@RequestMapping("/delete")
	@DeleteMapping
	public void delete(@RequestBody Dish d) {
		repo.deleteById(d.getId());
	}
}

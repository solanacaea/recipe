package com.food.recipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.entries.Dish;
import com.food.recipe.repositories.DishRepo;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@RequestMapping("/dish")
@CommonsLog
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
	@PutMapping
	public void save(@RequestBody Dish d) {
		log.info(d);
	}
}

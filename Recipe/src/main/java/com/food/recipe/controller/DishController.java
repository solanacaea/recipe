package com.food.recipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.entries.Dish;
import com.food.recipe.repositories.DishRepo;

@RestController
@RequestMapping("/dish")
public class DishController {

	@Autowired
	private DishRepo repo;
	
	@RequestMapping("/save")
	@PostMapping
	public void save(@RequestBody Dish c) {
		repo.save(c);
	}
	
	@RequestMapping("/delete")
	@PostMapping
	public void delete(@RequestParam int id) { 
		repo.deleteById(id);
	}

	@RequestMapping("/get")
	@GetMapping
	public List<Dish> get() {
		return repo.findAll();
	}
}

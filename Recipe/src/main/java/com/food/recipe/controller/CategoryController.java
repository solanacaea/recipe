package com.food.recipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.entries.lkp.Category;
import com.food.recipe.repositories.lkp.CatagoryRepo;

@RestController
@RequestMapping("/catagory")
public class CategoryController {

	@Autowired
	private CatagoryRepo repo;
	
	@RequestMapping("/save")
	@PostMapping
	public void save(@RequestBody Category c) {
		repo.save(c);
	}
	
	@RequestMapping("/saveall")
	@PostMapping
	public void saveAll(@RequestBody List<Category> c) {
		repo.saveAll(c);
	}
	
	@RequestMapping("/delete/{id}")
	@PostMapping
	public void delete(@PathVariable(value = "id") Integer id) {
		repo.deleteById(id);
	}
	
	@RequestMapping("/get")
	@GetMapping
	public List<Category> get() {
		return repo.findAll();
	}
}

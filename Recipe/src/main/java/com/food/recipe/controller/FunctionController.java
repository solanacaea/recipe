package com.food.recipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.entries.lkp.Function;
import com.food.recipe.repositories.lkp.FunctionRepo;

@RestController
@RequestMapping("/function")
public class FunctionController {

	@Autowired
	private FunctionRepo repo;
	
	@RequestMapping("/save")
	@PostMapping
	public void save(@RequestBody Function c) {
		repo.save(c);
	}

	@RequestMapping("/saveall")
	@PostMapping
	public void saveAll(@RequestBody List<Function> c) {
		repo.saveAll(c);
	}
	
	@RequestMapping("/delete/{id}")
	@PostMapping
	public void delete(@PathVariable(value = "id") int id) {
		repo.deleteById(id);
	}

	@RequestMapping("/get")
	@GetMapping
	public List<Function> get() {
		return repo.findAll();
	}
}
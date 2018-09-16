package com.food.recipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.entries.lkp.Type;
import com.food.recipe.repositories.lkp.TypeRepo;

@RestController
@RequestMapping("/type")
public class TypeController {

	@Autowired
	private TypeRepo repo;
	
	@RequestMapping("/save")
	@PostMapping
	public void save(@RequestBody Type c) {
		repo.save(c);
	}
	
	@RequestMapping("/delete")
	@PostMapping
	public void delete(@RequestParam int id) {
		repo.deleteById(id);
	}

	@RequestMapping("/get")
	@GetMapping
	public List<Type> get() {
		return repo.findAll();
	}
}

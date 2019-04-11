package com.food.recipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.entries.lkp.Efficacy;
import com.food.recipe.repositories.lkp.EfficacyRepo;

@RestController
@RequestMapping("/efficacy")
public class EfficacyController {

	@Autowired
	private EfficacyRepo repo;
	
	@RequestMapping("/save")
	@PostMapping
	public void save(@RequestBody Efficacy c) {
		repo.save(c);
	}

	@RequestMapping("/saveall")
	@PostMapping
	public void saveAll(@RequestBody List<Efficacy> c) {
		repo.saveAll(c);
	}
	
	@RequestMapping("/delete/{id}")
	@PostMapping
	public void delete(@PathVariable(value = "id") int id) {
		repo.deleteById(id);
	}

	@RequestMapping("/get")
	@GetMapping
	public List<Efficacy> get() {
		return repo.findAll();
	}
}

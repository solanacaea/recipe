package com.food.recipe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.bean.DishBean;
import com.food.recipe.entries.Dish;
import com.food.recipe.entries.DishCategory;
import com.food.recipe.entries.DishFunction;
import com.food.recipe.entries.DishPeriod;
import com.food.recipe.entries.DishStage;
import com.food.recipe.entries.DishType;
import com.food.recipe.repositories.DishRepo;

@RestController
@RequestMapping("/dish")
public class DishController {

	@Autowired
	private DishRepo repo;
	
	@RequestMapping("/save")
	@PostMapping
	public Dish save(@RequestBody DishBean b) {
		Dish d = new Dish();
		d.setDishId(b.getDishId());
		d.setName(b.getName());
		d.setContent(b.getContent());
		d.setDescription(b.getDescription());
		
		List<DishCategory> cs = new ArrayList<>();
		b.getCategories().forEach(c -> {
			DishCategory dc = new DishCategory();
			dc.setCategory(c);
			dc.setDish(d);
			cs.add(dc);
		});
		d.setCategories(cs);
		
		List<DishFunction> fs = new ArrayList<>();
		b.getFunctions().forEach(f -> {
			DishFunction df = new DishFunction();
			df.setFunction(f);
			df.setDish(d);
			fs.add(df);
		});
		d.setFunctions(fs);
		
		List<DishPeriod> ps = new ArrayList<>();
		b.getPeriods().forEach(p -> {
			DishPeriod dp = new DishPeriod();
			dp.setPeriod(p);
			dp.setDish(d);
			ps.add(dp);
		});
		d.setPeriods(ps);
		
		List<DishStage> ss = new ArrayList<>();
		b.getStages().forEach(s -> {
			DishStage ds = new DishStage();
			ds.setStage(s);
			ds.setDish(d);
			ss.add(ds);
		});
		d.setStages(ss);
		
		List<DishType> ts = new ArrayList<>();
		b.getTypes().forEach(t -> {
			DishType dt = new DishType();
			dt.setType(t);
			dt.setDish(d);
			ts.add(dt);
		});
		d.setTypes(ts);
		
		return repo.save(d);
	}

	@RequestMapping("/delete/{id}")
	@PostMapping
	public void delete(@PathVariable(value = "id") int id) { 
		repo.deleteById(id);
	}

	@RequestMapping("/get/{id}")
	@GetMapping
	public Dish get(@PathVariable(value = "id") int id) {
		Optional<Dish> o = repo.findById(id);
		return o.isPresent() ? o.get() : null;
	}
	
	@RequestMapping("/getall")
	@GetMapping
	public List<Dish> getAll() {
		return repo.findAll();
	}
}

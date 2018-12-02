package com.food.recipe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.food.recipe.repositories.DishCatagoryRepo;
import com.food.recipe.repositories.DishFunctionRepo;
import com.food.recipe.repositories.DishPeriodRepo;
import com.food.recipe.repositories.DishRepo;
import com.food.recipe.repositories.DishStageRepo;
import com.food.recipe.repositories.DishTypeRepo;

@RestController
@RequestMapping("/dish")
public class DishController {

	@Autowired
	private DishRepo repo;
	
	@Autowired
	private DishCatagoryRepo dcRepo;
	
	@Autowired
	private DishFunctionRepo dfRepo;
	
	@Autowired
	private DishPeriodRepo dpRepo;
	
	@Autowired
	private DishStageRepo dsRepo;
	
	@Autowired
	private DishTypeRepo dtRepo;
	
	@RequestMapping("/save")
	@PostMapping
	public Dish save(@RequestBody DishBean b) {
		Dish d = new Dish();
		d.setDishId(b.getDishId());
		d.setName(b.getName());
		d.setContent(b.getContent());
		d.setDescription(b.getDescription());
		Dish savedDish = repo.save(d);
		
		List<DishCategory> cs = new ArrayList<>();
		b.getCategories().forEach(c -> {
			DishCategory dc = new DishCategory();
			dc.setCategory(c);
			dc.setDish(d);
			cs.add(dc);
			dcRepo.save(dc);
		});
//		d.setCategories(cs);
		
		List<DishFunction> fs = new ArrayList<>();
		b.getFunctions().forEach(f -> {
			DishFunction df = new DishFunction();
			df.setFunction(f);
			df.setDish(d);
			fs.add(df);
			dfRepo.save(df);
		});
//		d.setFunctions(fs);
		
		List<DishPeriod> ps = new ArrayList<>();
		b.getPeriods().forEach(p -> {
			DishPeriod dp = new DishPeriod();
			dp.setPeriod(p);
			dp.setDish(d);
			ps.add(dp);
			dpRepo.save(dp);
		});
//		d.setPeriods(ps);
		
		List<DishStage> ss = new ArrayList<>();
		b.getStages().forEach(s -> {
			DishStage ds = new DishStage();
			ds.setStage(s);
			ds.setDish(d);
			ss.add(ds);
			dsRepo.save(ds);
		});
//		d.setStages(ss);
		
		List<DishType> ts = new ArrayList<>();
		b.getTypes().forEach(t -> {
			DishType dt = new DishType();
			dt.setType(t);
			dt.setDish(d);
			ts.add(dt);
			dtRepo.save(dt);
		});
//		d.setTypes(ts);
		
		return repo.findById(savedDish.getDishId()).get();
	}

	@RequestMapping("/delete/{id}")
	@PostMapping
	public void delete(@PathVariable(value = "id") int id) { 
		repo.deleteById(id);
	}

	@RequestMapping("/get/{id}")
	@GetMapping
	public DishBean get(@PathVariable(value = "id") int id) {
		Optional<Dish> o = repo.findById(id);
		if (!o.isPresent())
			return null;
		
		return convertDishToBean(o.get());
	}
	
	@RequestMapping("/getall")
	@GetMapping
	public List<DishBean> getAll() {
		return repo.findAll().stream().map(m -> convertDishToBean(m)).collect(Collectors.toList());
	}
	
	private DishBean convertDishToBean(Dish d) {
		DishBean b = new DishBean();
		b.setDishId(d.getDishId());
		b.setName(d.getName());
		b.setContent(d.getContent());
		b.setCategories(d.getCategories().stream().map(m -> m.getCategory()).collect(Collectors.toList()));
		b.setFunctions(d.getFunctions().stream().map(m -> m.getFunction()).collect(Collectors.toList()));
		b.setPeriods(d.getPeriods().stream().map(m -> m.getPeriod()).collect(Collectors.toList()));
		b.setStages(d.getStages().stream().map(m -> m.getStage()).collect(Collectors.toList()));
		b.setTypes(d.getTypes().stream().map(m -> m.getType()).collect(Collectors.toList()));
		return b;
	}
}

package com.food.recipe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.bean.DishBean;
import com.food.recipe.bean.RecipeBean;
import com.food.recipe.bean.RecipeBean.Daily;
import com.food.recipe.entries.Dish;
import com.food.recipe.entries.DishCategory;
import com.food.recipe.entries.DishEfficacy;
import com.food.recipe.entries.DishOptimalStage;
import com.food.recipe.entries.DishOptimalTime;
import com.food.recipe.entries.DishProperty;
import com.food.recipe.repositories.DishCatagoryRepo;
import com.food.recipe.repositories.DishEfficacyRepo;
import com.food.recipe.repositories.DishOptimalStageRepo;
import com.food.recipe.repositories.DishOptimalTimeRepo;
import com.food.recipe.repositories.DishPropertyRepo;
import com.food.recipe.repositories.DishRepo;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@RequestMapping("/dish")
@CommonsLog
public class DishController {

	@Autowired
	private DishRepo repo;
	
	@Autowired
	private DishCatagoryRepo dcRepo;
	
	@Autowired
	private DishEfficacyRepo dfRepo;
	
	@Autowired
	private DishOptimalTimeRepo dpRepo;
	
	@Autowired
	private DishOptimalStageRepo dsRepo;
	
	@Autowired
	private DishPropertyRepo dtRepo;
	
	@RequestMapping("/save")
	@PostMapping
	public DishBean save(@RequestBody DishBean b) {
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
		
		List<DishEfficacy> fs = new ArrayList<>();
		b.getEfficacies().forEach(f -> {
			DishEfficacy df = new DishEfficacy();
			df.setEfficacy(f);
			df.setDish(d);
			fs.add(df);
			dfRepo.save(df);
		});
//		d.setFunctions(fs);
		
		List<DishOptimalTime> ps = new ArrayList<>();
		b.getOptimalTimes().forEach(p -> {
			DishOptimalTime dp = new DishOptimalTime();
			dp.setOptimalTime(p);
			dp.setDish(d);
			ps.add(dp);
			dpRepo.save(dp);
		});
//		d.setPeriods(ps);
		
		List<DishOptimalStage> ss = new ArrayList<>();
		b.getOptimalStages().forEach(s -> {
			DishOptimalStage ds = new DishOptimalStage();
			ds.setOptimalStage(s);
			ds.setDish(d);
			ss.add(ds);
			dsRepo.save(ds);
		});
//		d.setStages(ss);
		
		List<DishProperty> ts = new ArrayList<>();
		b.getProperties().forEach(t -> {
			DishProperty dt = new DishProperty();
			dt.setProperty(t);
			dt.setDish(d);
			ts.add(dt);
			dtRepo.save(dt);
		});
//		d.setTypes(ts);
		
		return get(savedDish.getDishId());
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
		b.setEfficacies(d.getEfficacies().stream().map(m -> m.getEfficacy()).collect(Collectors.toList()));
		b.setOptimalTimes(d.getOptimalTimes().stream().map(m -> m.getOptimalTime()).collect(Collectors.toList()));
		b.setOptimalStages(d.getOptimalStages().stream().map(m -> m.getOptimalStage()).collect(Collectors.toList()));
		b.setProperties(d.getProperties().stream().map(m -> m.getProperty()).collect(Collectors.toList()));
		return b;
	}

	@RequestMapping("/recipe")
	@PostMapping
	public List<RecipeBean> getCustomRecipe(@RequestBody DishBean b) {
		
//		List<Integer> cates = b.getCategories().stream().map(m -> m.getId()).collect(Collectors.toList());
		List<Integer> f = b.getEfficacies().stream().map(m -> m.getId()).collect(Collectors.toList());
//		List<Integer> p = b.getOptimalTimes().stream().map(m -> m.getId()).collect(Collectors.toList());
//		List<Integer> s = b.getOptimalStages().stream().map(m -> m.getId()).collect(Collectors.toList());
		List<Integer> t = b.getProperties().stream().map(m -> m.getId()).collect(Collectors.toList());
//		
//		List<Dish> d = repo.findByParam(cates, f, p, s, t);
//		if (CollectionUtils.isEmpty(d)) 
//			return null;
		List<RecipeBean> rbs = new ArrayList<>();
		b.getOptimalStages().stream().forEach(stage -> {
			log.info("Querying for [" + stage.getName() + "] 适宜阶段的食谱...");
			RecipeBean rb = new RecipeBean();
			rb.setStage(stage);
			
			IntStream.range(1, 7).forEach(i -> {
				Daily daily = rb.new Daily();
				
				b.getOptimalTimes().forEach(time -> {
					log.info("Querying for [" + time.getName() + "] 适宜时间的食谱...");
					
					time.getCategoryTime().forEach(ct -> {
						log.info("Querying for [" + time.getName() + "] 类别的食谱...");
						List<Dish> dishes = repo.findByParam(ct.getCategory().getId(), f, time.getId(), stage.getId(), t);
						Random ran = new Random();
						int max = dishes.size();
						int min = 0;
						int randomInt = ran.nextInt(max)%(max-min+1) + min;
						Dish randomDish = dishes.get(randomInt);
						
						String cateKey = ct.getCategory().getName();
						if (daily.getMap().containsKey(cateKey)) {
							daily.getMap().get(cateKey).add(randomDish);
						} else {
							List<Dish> randomList = new ArrayList<>();
							randomList.add(randomDish);
							daily.getMap().put(cateKey, randomList);
						}
					});
					
				});
				
				rb.getDays().add(daily);
			});
			
			rbs.add(rb);
		});
		
		return rbs;
	}
}

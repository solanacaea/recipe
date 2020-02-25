package com.food.recipe.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.annotation.NoRepeatSubmit;
import com.food.recipe.bean.DailyBean;
import com.food.recipe.bean.RequestBean;
import com.food.recipe.entries.BackupDish;
import com.food.recipe.entries.Dish;
import com.food.recipe.repositories.BackupDao;
import com.food.recipe.repositories.DishRepo;
import com.food.recipe.service.DishService;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@RequestMapping("/dish")
@CommonsLog
@Transactional
public class DishController {

	@Autowired
	private DishRepo repo;
	
	@Autowired
	private BackupDao bkDao;
	
	@Autowired
	private DishService service;

	@NoRepeatSubmit
	@PostMapping("/getall")
	public List<Dish> getAll() {
		return repo.findAllDishes();
	}

	@NoRepeatSubmit
	@PostMapping("/recipe")
	public List<DailyBean> getCustomRecipe(@RequestBody RequestBean d) {
		return service.getCustomRecipe(d);
	}
	
	@NoRepeatSubmit
	@PostMapping("/save")
	public void save(@RequestBody Dish d) {
		if (StringUtils.isBlank(d.getContent())) {
			throw new NullPointerException("食材内容不能为空!");
		}
		d.setContent(d.getContent().replace("，", ","));
		d.setUpdatedDate(new Date());
		repo.saveAndFlush(d);
		log.info("Saved " + d);
	}
	
	@NoRepeatSubmit
	@DeleteMapping("/delete")
	public void delete(@RequestBody Dish d) {
		log.info("deleting dish " + d);
		repo.deleteById(d.getId());
		CompletableFuture.runAsync(() -> {
			bkDao.auditBackup(d, new BackupDish());
		});
	}
	
}

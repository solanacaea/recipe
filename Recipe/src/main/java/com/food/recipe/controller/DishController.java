package com.food.recipe.controller;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.annotation.NoRepeatSubmit;
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
public class DishController {

	@Autowired
	private DishRepo repo;
	
	@Autowired
	private BackupDao bkDao;
	
	@Autowired
	private DishService service;

	@NoRepeatSubmit
	@PostMapping("/getall")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(ResponseEntity.ok(
				repo.findAllDishes()), HttpStatus.OK);
	}

	@NoRepeatSubmit
	@PostMapping("/recipe")
	public ResponseEntity<?> getCustomRecipe(@RequestBody RequestBean d) {
		return new ResponseEntity<>(ResponseEntity.ok(
				service.getCustomRecipe(d)), HttpStatus.OK);
	}
	
	@NoRepeatSubmit
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody Dish d) {
		if (StringUtils.isBlank(d.getContent())) {
			throw new NullPointerException("食材内容不能为空!");
		}
		d.setContent(d.getContent().replace("，", ","));
		d.setUpdatedDate(new Date());
		repo.saveAndFlush(d);
		log.info("Saved " + d);
		return new ResponseEntity<>(ResponseEntity.ok("save succeed!"), HttpStatus.OK);
	}

	@NoRepeatSubmit
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody Dish d) {
		log.info("deleting dish " + d);
		repo.deleteById(d.getId());
		CompletableFuture.runAsync(() -> {
			bkDao.auditBackup(d, new BackupDish());
		});
		return new ResponseEntity<>(ResponseEntity.ok("delete succeed!"), HttpStatus.OK);
	}
	
}

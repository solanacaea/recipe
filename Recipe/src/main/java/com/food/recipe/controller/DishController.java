package com.food.recipe.controller;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.annotation.NoRepeatSubmit;
import com.food.recipe.bean.RequestBean;
import com.food.recipe.common.HttpResponse;
import com.food.recipe.common.HttpResponseEntity;
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
	public HttpResponse<?> getAll() {
		return new HttpResponse<>(HttpResponseEntity.response(
				repo.findAllDishes()));
	}

	@NoRepeatSubmit
	@PostMapping("/recipe")
	public HttpResponse<?> getCustomRecipe(@RequestBody RequestBean d) {
		return new HttpResponse<>(HttpResponseEntity.response(
				service.getCustomRecipe(d)));
	}
	
	@NoRepeatSubmit
	@PostMapping("/save")
	public HttpResponse<?> save(@RequestBody Dish d) {
		if (StringUtils.isBlank(d.getContent())) {
			return new HttpResponse<>(HttpResponseEntity.response(
					"食材内容不能为空!", HttpStatus.BAD_REQUEST));
		}
		d.setContent(d.getContent().replace("，", ","));
		d.setUpdatedDate(new Date());
		repo.saveAndFlush(d);
		log.info("Saved " + d);
		return new HttpResponse<>(HttpResponseEntity.response("Save successful!"));
	}

	@NoRepeatSubmit
	@PostMapping("/delete")
	public HttpResponse<?> delete(@RequestBody Dish d) {
		log.info("deleting dish " + d);
		repo.deleteById(d.getId());
		CompletableFuture.runAsync(() -> {
			bkDao.auditBackup(d, new BackupDish());
		});
		return new HttpResponse<>(HttpResponseEntity.response("Deleted successful!"));
	}
	
}

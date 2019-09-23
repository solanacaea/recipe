package com.food.recipe.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.bean.DailyBean;
import com.food.recipe.bean.RequestBean;
import com.food.recipe.entries.Dish;
import com.food.recipe.entries.UserAudit;
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
	private DishService service;
	
	@RequestMapping("/getall")
	@GetMapping
	public List<Dish> getAll() {
		return repo.findAllDishes();
	}

	@RequestMapping("/recipe")
	@PostMapping
	public List<DailyBean> getCustomRecipe(@RequestBody RequestBean d) {
		return service.getCustomRecipe(d);
	}
	
	@RequestMapping("/save")
	@PostMapping
	public void save(@RequestBody Dish d) {
		d.setContent(d.getContent().replace("，", ","));
		d.setUpdatedDate(new Date());
		repo.saveAndFlush(d);
		log.info("Saved " + d);
	}
	
	@RequestMapping("/delete")
	@DeleteMapping
	public void delete(@RequestBody Dish d) {
		repo.deleteById(d.getId());
	}
	
	@RequestMapping("/generate/getall")
	@PostMapping
	public List<UserAudit> getAllGenerater() throws IOException {
		return service.findAllGenerates();
	}
	
	@RequestMapping("/generate")
	@PostMapping
	public void generate(HttpServletResponse response, @RequestBody RequestBean b) throws IOException {
		Workbook workbook = service.generateRecipe(b);
		
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + b.getUserName() + ".xlsx");
		workbook.write(response.getOutputStream());
		log.info("Generated recipe, " + b);
	}
	
	@RequestMapping("/test/{name}")
	@PostMapping
	public void test(@PathVariable("name") String name, HttpServletResponse response) throws IOException {
		RequestBean d = new RequestBean();
		d.setName(name);
		d.setEfficacy("补血,活血,清热,祛湿");
//		List<DailyBean> data = service.getCustomRecipe(d);
		Workbook workbook = service.generateRecipe(d);
		
		response.setContentType("application/octet-stream; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + name + ".xlsx");
		workbook.write(response.getOutputStream());
		log.info("Generated recipe, " + d);
	}
	
}

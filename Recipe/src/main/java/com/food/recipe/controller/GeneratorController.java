package com.food.recipe.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.bean.RequestBean;
import com.food.recipe.entries.BackupUserAudit;
import com.food.recipe.entries.UserAudit;
import com.food.recipe.repositories.BackupDao;
import com.food.recipe.repositories.UserAuditRepo;
import com.food.recipe.service.DishService;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@RequestMapping("/generate")
@CommonsLog
@Transactional
public class GeneratorController {

	@Autowired
	private UserAuditRepo repo;
	
	@Autowired
	private BackupDao bkDao;
	
	@Autowired
	private DishService service;
	
	@RequestMapping("/getall")
	@PostMapping
	public List<UserAudit> getAllGenerater() throws IOException {
		return service.findAllGenerates();
	}
	
	@RequestMapping("/generate")
	@PostMapping
	public void generate(HttpServletResponse response, @RequestBody RequestBean b) throws IOException {
		log.info("Generating recipe " + b); 
		Workbook workbook = service.generateRecipe(b);
		
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + b.getUserName() + ".xlsx");
		workbook.write(response.getOutputStream());
		log.info("Generated recipe for=[" + b.getUserName() + "].");
	}
	
	@RequestMapping("/delete")
	@DeleteMapping
	public void delete(@RequestBody UserAudit u) {
		log.info("deleting generate record " + u);
		repo.deleteById(u.getId());
		CompletableFuture.runAsync(() -> {
			bkDao.auditBackup(u, new BackupUserAudit());
		});
	}
	
	
}

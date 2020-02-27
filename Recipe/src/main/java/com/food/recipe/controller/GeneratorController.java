package com.food.recipe.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.annotation.NoRepeatSubmit;
import com.food.recipe.bean.RequestBean;
import com.food.recipe.entries.BackupUserAudit;
import com.food.recipe.entries.UserGenerateAudit;
import com.food.recipe.repositories.BackupDao;
import com.food.recipe.repositories.UserAuditRepo;
import com.food.recipe.service.DishService;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@RequestMapping("/generate")
@CommonsLog
public class GeneratorController {

	@Autowired
	private UserAuditRepo repo;
	
	@Autowired
	private BackupDao bkDao;
	
	@Autowired
	private DishService service;
	
	@NoRepeatSubmit
	@PostMapping("/getall")
	public ResponseEntity<?> getAllGenerater() throws IOException {
		return new ResponseEntity<>(ResponseEntity.ok(
				service.findAllGenerates()), HttpStatus.OK);
	}
	
	@NoRepeatSubmit
	@PostMapping("/generate")
	public ResponseEntity<?> generate(HttpServletResponse response, @RequestBody RequestBean b) throws IOException {
		log.info("Generating recipe " + b); 
		Workbook workbook = service.generateRecipe(b);
		
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + b.getUserName() + ".xlsx");
		workbook.write(response.getOutputStream());
		log.info("Generated recipe for=[" + b.getUserName() + "].");
		return new ResponseEntity<>(ResponseEntity.ok("save succeed!"), HttpStatus.OK);
	}
	
	@NoRepeatSubmit
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody UserGenerateAudit u) {
		log.info("deleting generate record " + u);
		repo.deleteById(u.getId());
		CompletableFuture.runAsync(() -> {
			bkDao.auditBackup(u, new BackupUserAudit());
		});
		return new ResponseEntity<>(ResponseEntity.ok("delete succeed!"), HttpStatus.OK);
	}
	
	
}

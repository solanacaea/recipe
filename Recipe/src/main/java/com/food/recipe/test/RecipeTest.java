package com.food.recipe.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.food.recipe.bean.DishBean;
import com.food.recipe.entries.Dish;
import com.food.recipe.entries.DishCategory;
import com.food.recipe.entries.lkp.Category;
import com.food.recipe.entries.lkp.Efficacy;
import com.food.recipe.entries.lkp.OptimalTime;
import com.food.recipe.entries.lkp.OptimalStage;
import com.food.recipe.entries.lkp.Property;

public class RecipeTest {


	public static void main(String[] args) {
//		addCategory();
//		addFunction();
//		addPeriod();
//		addStage();
//		addType();
//		addDish();
//		getDish();
		getRecipe();
//		testJoin();
//		deadLock();
	}
	
	private static void deadLock() {
		Dish d = new Dish();
		Category c = new Category();
		
		DishCategory dc = new DishCategory();
		dc.setDish(d);
		d.setCategories(Arrays.asList(dc));
		System.out.println(d);
	}
	
	private static void testJoin() {
		List<Category> cates = new ArrayList<>();
		Category c1 = new Category();
		c1.setShortName("C1");
		cates.add(c1);
		Category c2 = new Category();
		c2.setShortName("C2");
		cates.add(c2);
		String categories = cates.stream().map(m -> m.getShortName()).collect(Collectors.joining(","));
		System.out.println(categories);
	}
	
	private static void getRecipe() {
		String host = "http://localhost:8080";
		String cateURL = "/dish/recipe";
		
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	
		DishBean b = new DishBean();
		Category c1 = new Category();
		c1.setId(1);
		Category c2 = new Category();
		c2.setId(33);
		b.setCategories(Arrays.asList(c1, c2));
		
		Efficacy f1 = new Efficacy();
		f1.setId(1);
		b.setEfficacies(Arrays.asList(f1));
		
		OptimalTime p1 = new OptimalTime();
		p1.setId(1);
		b.setOptimalTimes(Arrays.asList(p1));
		
		OptimalStage s1 = new OptimalStage();
		s1.setId(1);
		b.setOptimalStages(Arrays.asList(s1));
		
		Property t1 = new Property();
		t1.setId(1);
		b.setProperties(Arrays.asList(t1));
		
		HttpEntity<DishBean> entity = new HttpEntity<DishBean>(b, headers);
		ResponseEntity<List> resp = rt.postForEntity(host + cateURL, entity, List.class);
		System.out.println(resp.getBody());
		//ResponseEntity<Void> resp = rt.getForEntity(host + cateURL, Void.class, b);
	}
	
	private static void getDish() {
		String host = "http://localhost:8080";
		String cateURL = "/dish/get/{id}";
		
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	
		ResponseEntity<DishBean> resp = rt.getForEntity(host + cateURL, DishBean.class, 33);
		System.out.println(resp.getBody());
	}

	private static void addDish() {
		String host = "http://localhost:8080";
		String cateURL = "/dish/save";
		DishBean b = new DishBean();
		b.setDishId(33);
		b.setName("First Dish");
		b.setContent("aaaaaaaaaaaaaaa");
		
		List<Category> cs = new ArrayList<>();
		Category c = new Category();
		c.setId(1);
		c.setName("Category2");
		c.setValue("Category2");
		c.setShortName("CATEGORY2");
		cs.add(c);
		Category c2 = new Category();
		c2.setId(33);
		cs.add(c2);
		Category c3 = new Category();
		c3.setId(65);
		cs.add(c3);
		Category c4 = new Category();
		c4.setId(66);
		cs.add(c4);
		b.setCategories(cs);
		
		Efficacy f = new Efficacy();
		f.setId(1);
		f.setName("Function1");
		f.setValue("Function1");
		f.setShortName("FUNCTION1");
		b.setEfficacies(Arrays.asList(f));
		
		OptimalTime p = new OptimalTime();
		p.setId(1);
		p.setName("Period1");
		p.setValue("Period1");
		p.setShortName("PERIOD1");
		b.setOptimalTimes(Arrays.asList(p));
		
		OptimalStage s = new OptimalStage();
		s.setId(1);
		s.setName("Stage1");
		s.setValue("Stage1");
		s.setShortName("STAGE1");
		b.setOptimalStages(Arrays.asList(s));

		Property t = new Property();
		t.setId(1);
		t.setName("Type1");
		t.setValue("Type1");
		t.setShortName("TYPE2");
		b.setProperties(Arrays.asList(t));
		
		
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	
		HttpEntity<DishBean> entity = new HttpEntity<DishBean>(b, headers);
		rt.postForEntity(host + cateURL, entity, Void.class);
	}
	
	private static void addCategory() {
		String host = "http://localhost:8080";
		String cateURL = "/catagory/save";
		Category c = new Category();
		c.setName("Category4");
		c.setValue("Category4");
		c.setShortName("CATEGORY4");

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Category> entity = new HttpEntity<Category>(c, headers);
		rt.postForEntity(host + cateURL, entity, Void.class);
	}

	private static void addFunction() {
		String host = "http://localhost:8080";
		String cateURL = "/function/save";
		Efficacy c = new Efficacy();
		c.setName("Function1");
		c.setValue("Function1");
		c.setShortName("FUNCTION1");

		RestTemplate rt = new RestTemplate();
		//		ParameterizedTypeReference<Category> myBean =
		//			     new ParameterizedTypeReference<Category>() {};
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Efficacy> entity = new HttpEntity<Efficacy>(c, headers);
		rt.postForEntity(host + cateURL, entity, Void.class);
	}

	private static void addPeriod() {
		String host = "http://localhost:8080";
		String cateURL = "/period/save";
		OptimalTime c = new OptimalTime();
		c.setName("Period1");
		c.setValue("Period1");
		c.setShortName("PERIOD1");

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<OptimalTime> entity = new HttpEntity<OptimalTime>(c, headers);
		rt.postForEntity(host + cateURL, entity, Void.class);
	}

	private static void addStage() {
		String host = "http://localhost:8080";
		String cateURL = "/stage/save";
		OptimalStage c = new OptimalStage();
		c.setName("Stage1");
		c.setValue("Stage1");
		c.setShortName("STAGE1");

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<OptimalStage> entity = new HttpEntity<OptimalStage>(c, headers);
		rt.postForEntity(host + cateURL, entity, Void.class);
	}

	private static void addType() {
		String host = "http://localhost:8080";
		String cateURL = "/type/save";
		Property c = new Property();
		c.setName("Type1");
		c.setValue("Type1");
		c.setShortName("TYPE2");
	
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	
		HttpEntity<Property> entity = new HttpEntity<Property>(c, headers);
		rt.postForEntity(host + cateURL, entity, Void.class);
	}
	
	
	
}

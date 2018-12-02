package com.food.recipe.test;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.food.recipe.bean.DishBean;
import com.food.recipe.entries.Dish;
import com.food.recipe.entries.lkp.Category;
import com.food.recipe.entries.lkp.Function;
import com.food.recipe.entries.lkp.Period;
import com.food.recipe.entries.lkp.Stage;
import com.food.recipe.entries.lkp.Type;

public class RecipeTest {


	public static void main(String[] args) {
//		addCategory();
//		addFunction();
//		addPeriod();
//		addStage();
//		addType();
//		addDish();
		getDish();
		
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
		b.setName("First Dish");
		b.setContent("safdsafdsafdsa");
		
		Category c = new Category();
		c.setId(1);
		c.setName("Category2");
		c.setValue("Category2");
		c.setShortName("CATEGORY2");
		b.setCategories(Arrays.asList(c));
		
		Function f = new Function();
		f.setId(1);
		f.setName("Function1");
		f.setValue("Function1");
		f.setShortName("FUNCTION1");
		b.setFunctions(Arrays.asList(f));
		
		Period p = new Period();
		p.setId(1);
		p.setName("Period1");
		p.setValue("Period1");
		p.setShortName("PERIOD1");
		b.setPeriods(Arrays.asList(p));
		
		Stage s = new Stage();
		s.setId(1);
		s.setName("Stage1");
		s.setValue("Stage1");
		s.setShortName("STAGE1");
		b.setStages(Arrays.asList(s));

		Type t = new Type();
		t.setId(1);
		t.setName("Type1");
		t.setValue("Type1");
		t.setShortName("TYPE2");
		b.setTypes(Arrays.asList(t));
		
		
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
		c.setName("Category1");
		c.setValue("Category1");
		c.setShortName("CATEGORY1");

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Category> entity = new HttpEntity<Category>(c, headers);
		rt.postForEntity(host + cateURL, entity, Void.class);
	}

	private static void addFunction() {
		String host = "http://localhost:8080";
		String cateURL = "/function/save";
		Function c = new Function();
		c.setName("Function1");
		c.setValue("Function1");
		c.setShortName("FUNCTION1");

		RestTemplate rt = new RestTemplate();
		//		ParameterizedTypeReference<Category> myBean =
		//			     new ParameterizedTypeReference<Category>() {};
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Function> entity = new HttpEntity<Function>(c, headers);
		rt.postForEntity(host + cateURL, entity, Void.class);
	}

	private static void addPeriod() {
		String host = "http://localhost:8080";
		String cateURL = "/period/save";
		Period c = new Period();
		c.setName("Period1");
		c.setValue("Period1");
		c.setShortName("PERIOD1");

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Period> entity = new HttpEntity<Period>(c, headers);
		rt.postForEntity(host + cateURL, entity, Void.class);
	}

	private static void addStage() {
		String host = "http://localhost:8080";
		String cateURL = "/stage/save";
		Stage c = new Stage();
		c.setName("Stage1");
		c.setValue("Stage1");
		c.setShortName("STAGE1");

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Stage> entity = new HttpEntity<Stage>(c, headers);
		rt.postForEntity(host + cateURL, entity, Void.class);
	}

	private static void addType() {
		String host = "http://localhost:8080";
		String cateURL = "/type/save";
		Type c = new Type();
		c.setName("Type1");
		c.setValue("Type1");
		c.setShortName("TYPE2");
	
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	
		HttpEntity<Type> entity = new HttpEntity<Type>(c, headers);
		rt.postForEntity(host + cateURL, entity, Void.class);
	}
	
	
	
}

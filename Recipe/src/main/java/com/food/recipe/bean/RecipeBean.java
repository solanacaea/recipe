package com.food.recipe.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.food.recipe.entries.Dish;
import com.food.recipe.entries.lkp.OptimalStage;

import lombok.Data;

@Data
public class RecipeBean {

	private OptimalStage stage;
	private List<Daily> days = new ArrayList<>();
	
	@Data
	public class Daily {
		private Map<String, List<Dish>> map = new HashMap<>();
//		private List<Dish> breakfast;
//		private List<Dish> lunch;
//		private List<Dish> dinner;
//		
//		private List<Dish> morningDessert;
//		private List<Dish> dessert;
//		private List<Dish> eveningDessert;
	}
}
package com.food.recipe.bean;

import java.util.List;

import com.food.recipe.entries.lkp.Category;
import com.food.recipe.entries.lkp.Function;
import com.food.recipe.entries.lkp.Period;
import com.food.recipe.entries.lkp.Stage;
import com.food.recipe.entries.lkp.Type;

import lombok.Data;

@Data
public class DishBean {
	
	private int dishId;
	private String name;
	private String content;
	private String description;
	
	private List<Category> categories;
	private List<Function> functions;
	private List<Period> periods;
	private List<Stage> stages;
	private List<Type> types;
}

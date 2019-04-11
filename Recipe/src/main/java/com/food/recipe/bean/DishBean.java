package com.food.recipe.bean;

import java.util.List;

import com.food.recipe.entries.lkp.Category;
import com.food.recipe.entries.lkp.Efficacy;
import com.food.recipe.entries.lkp.OptimalTime;
import com.food.recipe.entries.lkp.OptimalStage;
import com.food.recipe.entries.lkp.Property;

import lombok.Data;

@Data
public class DishBean {
	
	private int dishId;
	private String name;
	private String content;
	private String description;
	
	private List<Category> categories;
	private List<Efficacy> efficacies;
	private List<OptimalTime> optimalTimes;
	private List<OptimalStage> optimalStages;
	private List<Property> properties;
}

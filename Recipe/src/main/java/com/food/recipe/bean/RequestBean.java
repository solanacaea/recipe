package com.food.recipe.bean;

import lombok.Data;

@Data
public class RequestBean {

	private String name;
	private String content;
	private String description;
	private String category;
	private String efficacy;
	private String optimalTime;
	private String optimalStage;
	private String property;
	
	private String userName;
	private String suggestion;
	private String remark;
	
}

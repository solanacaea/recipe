package com.food.recipe.entries;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table
@Data
@ToString
public class Dish {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
//	@Version
//	@JsonIgnore
//	private Long version;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Content")
	private String content;
	
	@Column(name = "Description")
	private String description;

	@Column(name="Category")
	private String category;
	
	@Column(name="Efficacy")
	private String efficacy;
	
	@Column(name="OptimalTime")
	private String optimalTime;
	
	@Column(name="OptimalStage")
	private String optimalStage;
	
	@Column(name="Property")
	private String property;
	
}

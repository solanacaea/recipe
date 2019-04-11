package com.food.recipe.entries;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table
@Data
@ToString
public class Dish {

	@Id
	@Column(name = "dishId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int dishId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "description")
	private String description;

	@ManyToMany
	@JoinTable(name="DishCategory", 
			joinColumns={@JoinColumn(name="dishId")},
			inverseJoinColumns={@JoinColumn(name="Id")})
	private List<DishCategory> categories;
	
	@ManyToMany
	@JoinTable(name="DishEfficacy", 
			joinColumns={@JoinColumn(name="dishId")},
			inverseJoinColumns={@JoinColumn(name="Id")})
	private List<DishEfficacy> efficacies;
	
	@ManyToMany
	@JoinTable(name="DishOptimalTime", 
			joinColumns={@JoinColumn(name="dishId")},
			inverseJoinColumns={@JoinColumn(name="Id")})
	private List<DishOptimalTime> optimalTimes;
	
	@ManyToMany
	@JoinTable(name="DishOptimalStage", 
			joinColumns={@JoinColumn(name="dishId")},
			inverseJoinColumns={@JoinColumn(name="Id")})
	private List<DishOptimalStage> optimalStages;
	
	@ManyToMany
	@JoinTable(name="DishProperty", 
			joinColumns={@JoinColumn(name="dishId")},
			inverseJoinColumns={@JoinColumn(name="Id")})
	private List<DishProperty> properties;
}

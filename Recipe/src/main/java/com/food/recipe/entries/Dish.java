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

@Entity
@Table
@Data
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
	@JoinTable(name="DishFunction", 
			joinColumns={@JoinColumn(name="dishId")},
			inverseJoinColumns={@JoinColumn(name="Id")})
	private List<DishFunction> functions;
	
	@ManyToMany
	@JoinTable(name="DishPeriod", 
			joinColumns={@JoinColumn(name="dishId")},
			inverseJoinColumns={@JoinColumn(name="Id")})
	private List<DishPeriod> periods;
	
	@ManyToMany
	@JoinTable(name="DishStage", 
			joinColumns={@JoinColumn(name="dishId")},
			inverseJoinColumns={@JoinColumn(name="Id")})
	private List<DishStage> stages;
	
	@ManyToMany
	@JoinTable(name="DishType", 
			joinColumns={@JoinColumn(name="dishId")},
			inverseJoinColumns={@JoinColumn(name="Id")})
	private List<DishType> types;
}

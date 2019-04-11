package com.food.recipe.entries;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.recipe.entries.lkp.Category;

import lombok.Data;
import lombok.ToString;

@Entity
@Table
@Data
@ToString(exclude = "dish")
public class DishCategory {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name="dishId")
	private Dish dish;

	@ManyToOne
    @JoinColumn(name="lkpCategoryId")
	private Category category;
}

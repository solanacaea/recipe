package com.food.recipe.entries;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.food.recipe.entries.lkp.Period;

import lombok.Data;

@Entity
@Table
@Data
public class DishPeriod {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
    @JoinColumn(name="dishId")
	private Dish dish;
	
	@ManyToOne
    @JoinColumn(name="lkpPeriodId")
	private Period period;
}

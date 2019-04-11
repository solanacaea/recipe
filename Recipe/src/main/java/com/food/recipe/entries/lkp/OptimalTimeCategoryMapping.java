package com.food.recipe.entries.lkp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class OptimalTimeCategoryMapping {

	@Id
	@Column(name = "mappingId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int mappingId;
	
	@ManyToOne
    @JoinColumn(name="lkpCategoryId")
	private Category category;
	
	@ManyToOne
    @JoinColumn(name="lkpOptimalTimeId")
	private OptimalTime optimalTime;
}

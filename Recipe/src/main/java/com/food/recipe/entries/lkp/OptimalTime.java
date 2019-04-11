package com.food.recipe.entries.lkp;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class OptimalTime extends BaseLookup {

	@ManyToMany
	@JoinTable(name="OptimalTimeCategoryMapping", 
			joinColumns={@JoinColumn(name="mappingId")},
			inverseJoinColumns={@JoinColumn(name="Id")})
	private List<OptimalTimeCategoryMapping> categoryTime;
}

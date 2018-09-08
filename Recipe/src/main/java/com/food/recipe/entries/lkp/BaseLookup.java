package com.food.recipe.entries.lkp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;


@MappedSuperclass
@Data
public class BaseLookup {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "shortName")
	private String shortName;
	
	@Column(name = "value")
	private String value;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "displayOrder")
	private String displayOrder;
	
}

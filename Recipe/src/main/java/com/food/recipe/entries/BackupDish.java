package com.food.recipe.entries;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class BackupDish extends BackupBean {

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
	
	@Column(name="UpdatedDate")
	private Date updatedDate;
	
	@Column(name="Ingredient")
	private String ingredient;
	
}

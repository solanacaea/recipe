package com.food.recipe.entries;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAudit {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name="Efficacy")
	private String efficacy;
	
	@Column(name="Remark")
	private String remark;
	
	@Column(name="Suggestion")
	private String suggestion;
	
	@Column(name="CreatedDdate")
	private Date createdDdate;
}

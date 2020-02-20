package com.food.recipe.entries;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@EqualsAndHashCode(of = "sourceId")
@Data
public class BackupBean {

	//backup fields
	@Column(name="DeleteDate")
	private Date deleteDate;
	
	@Column(name = "SourceId")
	private int sourceId;
}

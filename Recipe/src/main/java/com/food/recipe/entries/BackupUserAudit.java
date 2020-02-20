
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class BackupUserAudit extends BackupBean {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "UserName")
	private String userName;

	@Column(name="Efficacy")
	private String efficacy;

	@Column(name="Ingredient")
	private String ingredient;

	@Column(name="Memo")
	private String memo;

	@Column(name="Suggestion")
	private String suggestion;

	@Column(name="CreatedDate")
	private Date createdDate;

}

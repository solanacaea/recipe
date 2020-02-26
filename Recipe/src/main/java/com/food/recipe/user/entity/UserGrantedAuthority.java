package com.food.recipe.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGrantedAuthority {

//	@Id
//	@Column(name = "Id")
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private String id;
	
	@Id
	@Column(name = "UserId")
	private int userId;

	@Column(name = "Role")
	private String role;
	
	@Column(name = "GrantedDate")
	private LocalDateTime grantedDate;
}

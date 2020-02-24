package com.food.recipe.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Entity
@Table
@Data
@ToString
@Builder
public class UserGrantedAuthority {

	private String userId;
	private String role;
	private LocalDateTime grantedDate;
}

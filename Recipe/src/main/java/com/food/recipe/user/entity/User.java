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
public class User {

	private String userId;
	private String username;
	private String password;
	private String email;
	private String phone;
	private LocalDateTime registerDate;
	private boolean enable;
}

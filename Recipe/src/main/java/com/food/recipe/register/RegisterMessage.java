package com.food.recipe.register;

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
public class RegisterMessage {

	private String requestId;
	private String code;
	private String phone;
	private String email;
	private LocalDateTime createdDate;
	
}

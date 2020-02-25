package com.food.recipe.user.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.*;

@Entity
@Table
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGrantedAuthority {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@Column(name = "UserId")
	private int userId;

	@Column(name = "Role")
	private String role;

	@Column(name = "GrantedDate")
	private LocalDateTime grantedDate;
}

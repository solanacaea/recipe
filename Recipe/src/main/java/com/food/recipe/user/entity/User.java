package com.food.recipe.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
public class User {

	@Id
	@Column(name = "UserId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	
	@Column(name = "Username")
	private String username;

	@Column(name = "Nickname")
	private String nickname;

	@Column(name = "Password")
	private String password;

	@Column(name = "Email")
	private String email;

	@Column(name = "Phone")
	private String phone;

	@Column(name = "RegisterDate")
	private LocalDateTime registerDate;

	@Column(name = "LastLoginDate")
	private LocalDateTime lastLoginDate;

	@Column(name = "Enable")
	private boolean enable;
	
	@OneToOne
	@JoinColumn(name = "UserId", unique = true, nullable = false, updatable = false, insertable = true)
	private UserGrantedAuthority authority;
}

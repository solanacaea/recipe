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

	@Column(name = "Enable")
	private boolean enable;
}

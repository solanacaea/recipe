package com.food.recipe.register;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.*;

@Entity
@Table
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterMessage {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "RequestId")
	private String requestId;

	@Column(name = "Response")
	private String response;

	@Column(name = "code")
	private String code;

	@Column(name = "Phone")
	private String phone;

	@Column(name = "Email")
	private String email;

	@Column(name = "CreatedDate")
	private LocalDateTime createdDate;
}

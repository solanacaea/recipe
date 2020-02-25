package com.food.recipe.user;

import lombok.Data;

@Data
public class RegisterUser {
	private String username;
	private String nickname;
	private String password;
	private String email;
	private String phone;
	private String captcha;
}

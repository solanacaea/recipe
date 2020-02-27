package com.food.recipe.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.annotation.Authorization;
import com.food.recipe.annotation.CurrentUser;
import com.food.recipe.annotation.NoRepeatSubmit;
import com.food.recipe.login.TokenEntity;
import com.food.recipe.login.TokenService;
import com.food.recipe.sms.SmsService;
import com.food.recipe.sms.SmsTemplate;
import com.food.recipe.user.RecipeUser;
import com.food.recipe.user.RecipeUserDetailsService;
import com.food.recipe.user.RegisterUser;
import com.food.recipe.utils.MD5Util;

@RestController
@RequestMapping("/user")
public class RegisterController {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private SmsService smsService;
	
	@Autowired
	private RecipeUserDetailsService userService;
	
	@PostMapping("/captcha")
	@NoRepeatSubmit
	public ResponseEntity<?> sms(@RequestParam String phone) {
		return new ResponseEntity<>(ResponseEntity.ok(
				smsService.sendMessage(phone, SmsTemplate.REGISTER)), HttpStatus.OK);
	}
	
	@PostMapping("/checkname")
	public ResponseEntity<?> checkName(@RequestParam String username) {
		return new ResponseEntity<>(ResponseEntity.ok(
				userService.userExists(username)), HttpStatus.OK);
	}
	
	@PostMapping("/register")
	@NoRepeatSubmit
	public ResponseEntity<?> register(@RequestBody RegisterUser u) {
		userService.createUser(u);
		return new ResponseEntity<>(ResponseEntity.ok("注册成功"), HttpStatus.OK);
	}

	@PostMapping("/login")
	@NoRepeatSubmit
	public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String password) {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			return new ResponseEntity<>(ResponseEntity.ok("用户名或密码为空！"), HttpStatus.EXPECTATION_FAILED);
		}

		RecipeUser user = userService.loadUserByUsername(userName);
		if (user == null) {
			return new ResponseEntity<>(ResponseEntity.ok("用户不存在！"), HttpStatus.EXPECTATION_FAILED);
		}

		if (MD5Util.encode(password).equals(user.getPassword())) {
			TokenEntity tokenEntity = tokenService.createToken(String.valueOf(user.getUserId()));

			String authKey = tokenEntity.getUserId() + "@" + tokenEntity.getToken();
			return new ResponseEntity<>(ResponseEntity.ok(authKey), HttpStatus.OK);
		}
		return new ResponseEntity<>(ResponseEntity.ok("用户名或密码错误！"), HttpStatus.EXPECTATION_FAILED);
	}

	@Authorization
	@PostMapping("/logout")
	@NoRepeatSubmit
	public ResponseEntity<?> logout(@CurrentUser RecipeUser user) {
		tokenService.deleteToken(String.valueOf(user.getUserId()));
		return new ResponseEntity<>(ResponseEntity.ok("注销成功"), HttpStatus.OK);
	}
}

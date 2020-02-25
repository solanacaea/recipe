package com.food.recipe.controller;

import javax.servlet.http.HttpServletRequest;

import com.food.recipe.annotation.Authorization;
import com.food.recipe.annotation.CurrentUser;
import com.food.recipe.login.TokenEntity;
import com.food.recipe.login.TokenService;
import com.food.recipe.user.RecipeUser;
import com.food.recipe.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.annotation.NoRepeatSubmit;
import com.food.recipe.sms.SmsService;
import com.food.recipe.sms.SmsTemplate;
import com.food.recipe.user.RecipeUserDetailsService;
import com.food.recipe.user.RegisterUser;

@RestController
@RequestMapping("/user")
@Transactional
public class RegisterController {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private SmsService smsService;
	
	@Autowired
	private RecipeUserDetailsService userService;
	
	@PostMapping("/captcha")
	@NoRepeatSubmit
	public String sms(@RequestParam String phone) {
		return smsService.sendMessage(phone, SmsTemplate.REGISTER);
	}
	
	@PostMapping("/checkname")
	public boolean checkName(@RequestParam String username) {
		try {
			return userService.userExists(username);
		} catch (Exception e) {
			ExceptionUtils.getStackTrace(e);
			throw new RuntimeException("");
		}
	}
	
	@PostMapping("/register")
	@NoRepeatSubmit
	public String register(@RequestBody RegisterUser u) {
		try {
			userService.createUser(u);
			return "注册成功";
		} catch (Exception e) {
			ExceptionUtils.getStackTrace(e);
			return "注册失败";
		}
	}

	@PostMapping("/login")
	@NoRepeatSubmit
	public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String password) {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			throw new IllegalArgumentException("用户名或密码为空！");
		}

		RecipeUser user = userService.loadUserByUsername(userName);
		if (user == null) {
			throw new IllegalStateException("用户不存在！");
		}

		if (MD5Util.encode(password).equals(user.getPassword())) {
			TokenEntity tokenEntity = tokenService.createToken(String.valueOf(user.getUserId()));
			String authKey = tokenEntity.getUserId() + "@" + tokenEntity.getToken();
			return new ResponseEntity<>(ResponseEntity.ok(authKey), HttpStatus.OK);
		}
		throw new IllegalArgumentException("用户名或密码错误！");
	}

	@Authorization
	@PostMapping("/logout")
	@NoRepeatSubmit
	public ResponseEntity<?> logout(@CurrentUser RecipeUser user) {
		tokenService.deleteToken(String.valueOf(user.getUserId()));
		return new ResponseEntity<>(ResponseEntity.ok("注销成功"), HttpStatus.OK);
	}
}

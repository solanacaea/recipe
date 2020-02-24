package com.food.recipe.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.recipe.sms.SmsService;
import com.food.recipe.sms.SmsTemplate;
import com.food.recipe.user.RecipeUserDetailsService;
import com.food.recipe.user.entity.User;

@RestController
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private SmsService smsService;
	
	@Autowired
	private RecipeUserDetailsService userService;
	
	@RequestMapping("/sms")
	@PostMapping
	public String sms(HttpServletRequest request, @RequestParam String phone) {
		return smsService.sendMessage(phone, SmsTemplate.REGISTER);
	}
	
	@RequestMapping("/")
	@PostMapping
	public String register(@RequestBody User u) {
		try {
			userService.createUser(u);
			return "注册成功";
		} catch (Exception e) {
			ExceptionUtils.getStackTrace(e);
			return "注册失败";
		}
	}
	
}

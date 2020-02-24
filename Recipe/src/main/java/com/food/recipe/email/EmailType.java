package com.food.recipe.email;

public enum EmailType {

	REGISTER("注册验证码", "注册账号"),
	RESET("重置验证码", "修改密码");
	
	private final String subject;
	private final String content;
	
	EmailType(String subject, String content) {
		this.subject = subject;
		this.content = content;
	}
	
	public String subject() {
		return subject;
	}
	
	public String content() {
		return content;
	}
}

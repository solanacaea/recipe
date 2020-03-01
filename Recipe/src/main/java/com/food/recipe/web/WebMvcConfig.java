package com.food.recipe.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	@Bean
	public CurrentUserArgumentResolver currentUserArgumentResolver() {
		return new CurrentUserArgumentResolver();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(currentUserArgumentResolver());
		super.addArgumentResolvers(argumentResolvers);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
	        .allowedHeaders("Content-Type","X-Requested-With","accept,Origin","Access-Control-Request-Method","Access-Control-Request-Headers","Authorization")
	        .allowedMethods("*")
	        .allowedOrigins("*")
			.maxAge(3600)
			.allowCredentials(true);
	}
	
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/home").setViewName("home");
//		registry.addViewController("/").setViewName("home");
//		registry.addViewController("/hello").setViewName("hello");
//		registry.addViewController("/login").setViewName("login");
//	}

	
}
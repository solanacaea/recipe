package com.food.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication 
@EnableJpaRepositories
@ComponentScan(basePackages = { "com.food.recipe" })
@EntityScan(basePackages = { "com.food.recipe.entries" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
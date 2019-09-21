package com.food.recipe.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyBean {

	Breakfast breakfast;
	Lunch lunch;
	Dinner dinner;
	Snack snack;

	@Data
	@AllArgsConstructor
	public static class Breakfast {
		String mainFood;
		String soap;
		String cai;
	}

	@Data
	@AllArgsConstructor
	public static class Lunch {
		String mainFood;
		String soap;
		String dish1;
		String dish2;
	}

	@Data
	@AllArgsConstructor
	public static class Dinner {
		String mainFood;
		String soap;
		String dish1;
		String dish2;
	}

	@Data
	@AllArgsConstructor
	public static class Snack {
		String snack1;
		String snack2;
		String snack3;
	}

}

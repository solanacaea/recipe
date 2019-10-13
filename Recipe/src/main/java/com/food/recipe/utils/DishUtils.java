package com.food.recipe.utils;

import java.util.Set;

public class DishUtils {

	public static boolean containsAny(Set<String> s1, Set<String> s2) {
		return s1.stream().anyMatch(p -> s2.contains(p));
	}
}

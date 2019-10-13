package com.food.recipe.utils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import com.food.recipe.entries.Dish;

public class DishUtils {

	public static boolean containsAny(Set<String> s1, Set<String> s2) {
		return s1.stream().anyMatch(p -> s2.contains(p));
	}
	
	public static Dish findAnyMatch(List<Dish> list, String key, String...contents) {
		
		if (key != null) {
			boolean none = Stream.of(contents).filter(Objects::nonNull).noneMatch(p -> p.contains(key));
			if (none) {
				Optional<Dish> o = list.parallelStream().filter(p -> p.getContent().contains(key)).findAny();
				return o.isPresent() ? o.get() : null;
			}
		}
		return null;
	}
}

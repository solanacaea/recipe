package com.food.recipe.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.food.recipe.bean.DailyBean;
import com.food.recipe.bean.DailyBean.Breakfast;
import com.food.recipe.bean.DailyBean.Dinner;
import com.food.recipe.bean.DailyBean.Lunch;
import com.food.recipe.bean.DailyBean.Snack;
import com.food.recipe.bean.RequestBean;
import com.food.recipe.entries.Dish;
import com.food.recipe.entries.UserGenerateAudit;
import com.food.recipe.repositories.DishDao;
import com.food.recipe.utils.DishUtils;

@Service
public class DishService {

	@Autowired
	private DishDao dao;
	
	@Autowired
	private ExcelService service;
	
	public List<UserGenerateAudit> findAllGenerates() {
		return dao.findAllGenerates();
	}
	
	public Workbook generateRecipe(RequestBean d) throws IOException {
		dao.addCustomInfo(UserGenerateAudit.builder().userName(d.getUserName())
				.efficacy(d.getEfficacy()).ingredient(d.getIngredient())
				.createdDate(new Date()).memo(d.getMemo()).suggestion(d.getSuggestion()).build());
				//.declare(d.getDeclare()).feature(d.getFeature()).note(d.getNote()).type(d.getType())
		return service.merge(getCustomRecipe(d), d);
	}
	
	public List<DailyBean> getCustomRecipe(RequestBean d) {
		
		List<DailyBean> allWeeks = new ArrayList<>();

		//first week, pork liver everyday
		String special = null;
		if (d.getIngredient().contains("猪肉类")) {
			special = "猪肝";
		}
		allWeeks.addAll(weekDish(1, d.getEfficacy(), d.getIngredient(), special));
		
		IntStream.rangeClosed(2, 5).boxed().forEach(week -> {
			allWeeks.addAll(weekDish(week, d.getEfficacy(), d.getIngredient(), null));
		});
		return allWeeks;
	}
	
	private List<DailyBean> weekDish(int week, String eff, String ing, String contains) {
		
		List<DailyBean> weekBeans = new ArrayList<>();
		List<Dish> breakfastList = dao.getFood(week, "早餐", eff, ing);
		List<Dish> lunchList = dao.getFood(week, "午餐", eff, ing);
		List<Dish> dinnerList = dao.getFood(week, "晚餐", eff, ing);
		List<Dish> snackList = dao.getFood(week, "零食&茶饮", eff, ing);
		
		IntStream.rangeClosed(1, 7).boxed().forEach(day -> {//from Monday to Sunday
			weekBeans.add(dailyDish(breakfastList, lunchList, dinnerList, snackList, contains));
		});
		return weekBeans;
	}
	
	private DailyBean dailyDish(List<Dish> breakfastList, List<Dish> lunchList, List<Dish> dinnerList,
			List<Dish> snackList, String contains) {
		Set<String> contents = new HashSet<>();
		Breakfast breakfast = getBreakfast(breakfastList, contents);
//		System.out.println(contents);
		Lunch lunch = getLunch(lunchList, contents, contains);
//		System.out.println(contents);
		Dinner dinner = getDinner(dinnerList, contents, contains);
//		System.out.println(contents);
		Snack snack = getSnack(snackList, contents);
//		System.out.println(contents);
		return DailyBean.builder().breakfast(breakfast)
			.lunch(lunch)
			.dinner(dinner)
			.snack(snack)
			.build();
	}

	private DailyBean.Snack getSnack(List<Dish> list, Set<String> contents) {
		
		list = new ArrayList<>(list);
		if (CollectionUtils.isEmpty(list))
			return new DailyBean.Snack("", "", "");
		
		Dish dish1 = getDish(list, contents, "");list.remove(dish1);
		Dish dish2 = getDish(list, contents, "");list.remove(dish2);
		Dish dish3 = getDish(list, contents, "");list.remove(dish3);
		
		return new DailyBean.Snack(dish1.getName(), dish2.getName(), dish3.getName());
	}

	private Dish getDish(List<Dish> list, Set<String> c, String category) {
		
		if (CollectionUtils.isEmpty(list))
			return new Dish();

		List<Dish> dishList = null;
		if (category == "") {
			dishList = list;
		} else {
			dishList = list.stream().filter(p -> p.getCategory().contains(category)).collect(Collectors.toList());
		}
		if (CollectionUtils.isEmpty(dishList))
			return new Dish();
		
		int i = RandomUtils.nextInt(1, dishList.size());
		Dish d = dishList.get(i - 1);
		
		Set<String> content = Stream.of(d.getContent().split(",")).collect(Collectors.toSet());
		if (!DishUtils.containsAny(c, content)) {
			c.addAll(content);
			return d;
		}
		
		List<Dish> netList = dishList.stream().filter(p -> {
			Set<String> pcont = Stream.of(p.getContent().split(",")).collect(Collectors.toSet());
			return DishUtils.containsAny(c, pcont) ? false : true;
		}).collect(Collectors.toList());
		
		if (CollectionUtils.isEmpty(netList)) {
			c.addAll(content);
			return d;
		}
		return getDish(netList, c, category);
	}
	
	private DailyBean.Breakfast getBreakfast(List<Dish> list, Set<String> contents) {
		list = new ArrayList<>(list);
		
		Dish mainFood = getDish(list, contents, "主食");list.remove(mainFood);
		
//		list = list.stream().filter(p -> p.getCategory().contains("汤") || p.getCategory().contains("菜"))
//			.collect(Collectors.toList());
		
		String cate = "汤";
		boolean rand = RandomUtils.nextBoolean();
		if (rand) {
			cate = "菜";
		} 
		Dish subFood = getDish(list, contents, cate);
		
		return new DailyBean.Breakfast(mainFood.getName(), subFood.getName());
	}
	
	private DailyBean.Lunch getLunch(List<Dish> list, Set<String> contents, String contains) {
		list = new ArrayList<>(list);
		Dish d1 = getDish(list, contents, "主食");list.remove(d1);
		Dish d2 = getDish(list, contents, "汤");list.remove(d2);
		Dish d3 = getDish(list, contents, "菜");	list.remove(d3);
		Dish d4 = getDish(list, contents, "菜");
		
		if (contains != null && !contents.contains(contains)) {
			boolean replace = RandomUtils.nextBoolean();
			if (replace) {
				list = list.stream().filter(Objects::nonNull)
						.filter(p -> p.getContent().contains(contains))
						.collect(Collectors.toList());
				d4 = getDish(list, contents, "菜");
				contents.add(contains);
			}
		}
		list.remove(d4);
		
		return new DailyBean.Lunch(d1.getName(), d2.getName(), d4.getName(), d3.getName());
	}

	private DailyBean.Dinner getDinner(List<Dish> list, Set<String> contents, String contains) {
		list = new ArrayList<>(list);
		Dish d1 = getDish(list, contents, "主食");list.remove(d1);
		Dish d2 = getDish(list, contents, "汤");list.remove(d2);
		Dish d3 = getDish(list, contents, "菜");list.remove(d3);
		Dish d4 = getDish(list, contents, "菜");
		
		if (contains != null && !contents.contains(contains)) {
			list = list.stream().filter(Objects::nonNull)
					.filter(p -> p.getContent().contains(contains))
					.collect(Collectors.toList());
			d4 = getDish(list, contents, "菜");
			contents.add(contains);
		}
		list.remove(d4);
		
		return new DailyBean.Dinner(d1.getName(), d2.getName(), d4.getName(), d3.getName());
	}
	
}

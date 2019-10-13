package com.food.recipe.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import com.food.recipe.bean.RequestBean;
import com.food.recipe.entries.Dish;
import com.food.recipe.entries.UserAudit;
import com.food.recipe.repositories.DishDao;
import com.food.recipe.utils.DishUtils;

@Service
public class DishService {

	@Autowired
	private DishDao dao;
	
	@Autowired
	private ExcelService service;
	
	public List<UserAudit> findAllGenerates() {
		return dao.findAllGenerates();
	}
	
	public Workbook generateRecipe(RequestBean d) throws IOException {
		dao.addCustomInfo(UserAudit.builder().userName(d.getUserName())
				.efficacy(d.getEfficacy()).ingredient(d.getIngredient())
				.createdDate(new Date()).memo(d.getMemo()).suggestion(d.getSuggestion()).build());
				//.declare(d.getDeclare()).feature(d.getFeature()).note(d.getNote()).type(d.getType())
		return service.merge(getCustomRecipe(d), d);
	}
	
	public List<DailyBean> getCustomRecipe(RequestBean d) {
		
		List<DailyBean> allWeeks = new ArrayList<>();
		IntStream.rangeClosed(1, 6).boxed().forEach(week -> {
			allWeeks.addAll(weekDish(week, d.getEfficacy(), d.getIngredient()));
		});
		return allWeeks;
	}
	
	private List<DailyBean> weekDish(int week, String eff, String ing) {
		
		List<DailyBean> weekBeans = new ArrayList<>();
		List<Dish> breakfastList = dao.getFood(week, "早餐", eff, ing);
		List<Dish> lunchList = dao.getFood(week, "午餐", eff, ing);
		List<Dish> dinnerList = dao.getFood(week, "晚餐", eff, ing);
		List<Dish> snackList = dao.getFood(week, "零食&茶饮", eff, ing);
		
		//first week, pork liver everyday
		
		IntStream.rangeClosed(1, 7).boxed().forEach(day -> {//from Monday to Sunday
			weekBeans.add(
					dailyDish(breakfastList, lunchList, dinnerList, snackList));
		});
		return weekBeans;
	}
	
	private DailyBean dailyDish(List<Dish> breakfastList, List<Dish> lunchList, List<Dish> dinnerList,
			List<Dish> snackList) {
		Set<String> contents = new HashSet<>();
		return DailyBean.builder().breakfast(getBreakfast(breakfastList, contents))
			.lunch(getLunch(lunchList, contents))
			.dinner(getDinner(dinnerList, contents))
			.snack(getSnack(snackList))
			.build();
	}

	private DailyBean.Snack getSnack(List<Dish> list) {
		
		list = new ArrayList<>(list);
		if (CollectionUtils.isEmpty(list))
			return new DailyBean.Snack("", "", "");
		
		int i = RandomUtils.nextInt(1, list.size());
		Dish dish1 = list.get(i - 1);
		list.remove(dish1);
		
		Set<String> content1 = Stream.of(dish1.getContent().split(",")).collect(Collectors.toSet());
		Dish dish2 = getDish(list, content1, "");
		list.remove(dish2);
		
		Set<String> content2 = Stream.of(dish2.getContent().split(",")).collect(Collectors.toSet());
		content2.addAll(content1);
		Dish dish3 = getDish(list, content2, "");
		
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
			return DishUtils.containsAny(pcont, content) ? false : true;
		}).collect(Collectors.toList());
		
		if (CollectionUtils.isEmpty(netList)) {
			return d;
		}
		return getDish(netList, content, category);
	}
	
	private DailyBean.Breakfast getBreakfast(List<Dish> list, Set<String> contents) {
		
		Dish mainFood = getDish(list, contents, "主食");
		Dish soap = getDish(list, contents, "汤");
		Dish cai = getDish(list, contents, "菜");
		
		return new DailyBean.Breakfast(mainFood.getName(), soap.getName(), cai.getName());
	}
	
	private DailyBean.Lunch getLunch(List<Dish> list, Set<String> contents) {
		
		Dish d1 = getDish(list, contents, "主食");
		Dish d2 = getDish(list, contents, "汤");
		Dish d3 = getDish(list, contents, "菜");
		Dish d4 = getDish(list, contents, "菜");
		
		return new DailyBean.Lunch(d1.getName(), d2.getName(), d3.getName(), d4.getName());
	}

	private DailyBean.Dinner getDinner(List<Dish> list, Set<String> contents) {
		
		Dish d1 = getDish(list, contents, "主食");
		Dish d2 = getDish(list, contents, "汤");
		Dish d3 = getDish(list, contents, "菜");
		Dish d4 = getDish(list, contents, "菜");
		
		return new DailyBean.Dinner(d1.getName(), d2.getName(), d3.getName(), d4.getName());
	}

}

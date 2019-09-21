package com.food.recipe.repositories;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.food.recipe.entries.Dish;

@Repository
public class DishDao {

	@Autowired
	private EntityManager em;
	
	public List<Dish> getFood(int week, String optimalTime, String efficacy) {
		String from = "from Dish where optimalStage like :week and optimalTime like :optimalTime";
		String where = getWhereCondition(efficacy, "efficacy");
		String hql = from + where;
		String optimalStage = getOptimalStage(week);
		
		Query q = em.createQuery(hql);
		q.setParameter("week", optimalStage);
		q.setParameter("optimalTime", "%" + optimalTime + "%");
		return q.getResultList();
	}
	
	private String getWhereCondition(String str, String column) {
		String[] arr = str.split(",");
		StringBuilder sb = new StringBuilder(" and (");
		StringJoiner sj = new StringJoiner(" or ");
		Stream.of(arr).forEach(a -> {
			StringBuilder single = new StringBuilder();
			single.append(column).append(" like '%").append(a.trim()).append("%'");
			sj.add(single.toString());
		});
		sb.append(sj.toString()).append(")");
		return sb.toString();
	} 
	
	private String getOptimalStage(int i) {
		switch (i) {
			case 1:
				return "%��һ��%";
			case 2:
				return "%�ڶ���%";
			case 3:
				return "%������%";
			case 4:
				return "%������%";
			case 5:
				return "%������%";
			case 6:
				return "%������%";
			case 7:
				return "%������%";
		}
		return "";
	}
}

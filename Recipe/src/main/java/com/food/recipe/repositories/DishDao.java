package com.food.recipe.repositories;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.food.recipe.entries.Dish;
import com.food.recipe.entries.UserGenerateAudit;

@Repository
public class DishDao {

	@Autowired
	private EntityManager em;
	
	public List<UserGenerateAudit> findAllGenerates() {
		String hql = "from UserGenerateAudit order by createdDate desc";
		TypedQuery<UserGenerateAudit> q = em.createQuery(hql, UserGenerateAudit.class);
		return q.getResultList();
	}
	
	public void addCustomInfo(UserGenerateAudit info) {
		String hql = "from UserGenerateAudit where userName = :userName";
		TypedQuery<UserGenerateAudit> q = em.createQuery(hql, UserGenerateAudit.class);
		q.setParameter("userName", info.getUserName());
		List<UserGenerateAudit> result = q.getResultList();
		if (CollectionUtils.isNotEmpty(result)) {
			UserGenerateAudit exists = result.get(0);
			info.setId(exists.getId());
		}
		em.merge(info);
	}
	
	public List<Dish> getFood(int week, String optimalTime, String efficacy, String ingredient) {
		String from = "from Dish where optimalStage like :week and optimalTime like :optimalTime";
		String where = getWhereCondition(efficacy, "efficacy");
		where += getWhereCondition(ingredient, "ingredient");
		String hql = from + where;
		String optimalStage = getOptimalStage(week);
		
		TypedQuery<Dish> q = em.createQuery(hql, Dish.class);
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
		sb.append(sj.toString()).append(") ");
		return sb.toString();
	} 
	
	private String getOptimalStage(int i) {
		switch (i) {
			case 1:
				return "%第一周%";
			case 2:
				return "%第二周%";
			case 3:
				return "%第三周%";
			case 4:
				return "%第四周%";
			case 5:
				return "%第五周%";
			case 6:
				return "%第六周%";
			case 7:
				return "%第七周%";
		}
		return "";
	}
}

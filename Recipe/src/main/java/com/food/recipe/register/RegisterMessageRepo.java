package com.food.recipe.register;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RegisterMessageRepo {

	@Autowired
	private EntityManager em;
	
	public void saveMessage(RegisterMessage msg) {
		em.merge(msg);
	}
	
	public RegisterMessage getMessage(String phone) {
		String hql = "SELECT * FROM register_message m1 where m1.phone = :phone " + 
				"and m1.created_date = (select max(created_date) from register_message m2 where m2.phone = m1.phone) " + 
				"and m1.created_date > date_sub(now(), interval 1 hour) ";
		Query q = em.createNativeQuery(hql, RegisterMessage.class);
		q.setParameter("phone", phone);
		try {
			return (RegisterMessage) q.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
}

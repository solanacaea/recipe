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
		String hql = "FROM RegisterMessage m1 WHERE m1.phone = :phone AND createdDate = " +
				"(SELECT MAX(createdDate) FROM RegisterMessage WHERE phone = m1.phone)";
		Query q = em.createQuery(hql);
		q.setParameter("phone", phone);
		try {
			return (RegisterMessage) q.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
}

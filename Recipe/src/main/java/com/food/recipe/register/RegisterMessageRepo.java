package com.food.recipe.register;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegisterMessageRepo {

	@Autowired
	private EntityManager em;
	
	public void saveMessage(RegisterMessage msg) {
		em.merge(msg);
	}
}

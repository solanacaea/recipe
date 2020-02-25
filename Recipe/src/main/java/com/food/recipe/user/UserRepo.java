package com.food.recipe.user;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.food.recipe.user.entity.User;
import com.food.recipe.user.entity.UserGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserRepo {

	@Autowired
	private EntityManager em;

	protected User createUser(User user) {
		return em.merge(user);
	}

	protected UserGrantedAuthority saveRole(UserGrantedAuthority role) {
		return em.merge(role);
	}

	protected void changePassword(String userId, String passwd) {
		
	}
	
	public void updateUser(User user) {
		
	}

	protected void deleteUser(String userId) {
		
	}

	protected boolean userExists(String userId) {
		return false;
	}

	protected User findByUserId(String userId) {
		Query q = em.createQuery("FROM User WHERE userId = :userId");
		q.setParameter(userId, userId);
		return (User) q.getSingleResult();
	}

	protected User findByUsername(String username) {
		Query q = em.createQuery("FROM User WHERE username = :username");
		q.setParameter("username", username);
		return (User) q.getSingleResult();
	}

	protected Collection<UserGrantedAuthority> getAuthByName(String username) {
		String hql = "FROM UserGrantedAuthority WHERE username = :username";
		TypedQuery<UserGrantedAuthority> q = em.createQuery(hql, UserGrantedAuthority.class);
		q.setParameter(username, username);
		return q.getResultList();
	}
	
}

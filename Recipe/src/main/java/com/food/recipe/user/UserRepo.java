package com.food.recipe.user;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.food.recipe.user.entity.User;
import com.food.recipe.user.entity.UserGrantedAuthority;

@Repository
public class UserRepo {

	@Autowired
	private EntityManager em;
	
	public User createUser(User user) {
		return em.merge(user);
	}
	
	public void saveRole(UserGrantedAuthority role) {
		
	}
	
	public void changePassword(String userId, String passwd) {
		
	}
	
	public void updateUser(User user) {
		
	}
	
	public void deleteUser(String userId) {
		
	}
	
	public boolean userExists(String userId) {
		return false;
	}
	
	public User getUserById(String userId) {
		Query q = em.createQuery("FROM User WHERE userId = :userId");
		q.setParameter("userId", userId);
		return (User) q.getSingleResult();
	}
	
	public Collection<UserGrantedAuthority> getAuthById(String userId) {
		TypedQuery<UserGrantedAuthority> q = em.createQuery(
				"FROM UserGrantedAuthority WHERE userId = :userId", UserGrantedAuthority.class);
		q.setParameter("userId", userId);
		return q.getResultList();
	}
	
}

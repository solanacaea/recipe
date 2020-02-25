package com.food.recipe.user;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.food.recipe.user.entity.User;
import com.food.recipe.user.entity.UserGrantedAuthority;

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

	protected void changePassword(String username, String passwd) {
		String hql = "UPDATE user SET password = :passwd WHERE username = :username";
		Query q = em.createQuery(hql);
		q.setParameter("passwd", passwd);
		q.setParameter("username", username);
		q.executeUpdate();
	}
	
	public void updateUser(RegisterUser user) {
		String hql = "UPDATE user SET email = :email, nickname=:nickname WHERE username = :username";
		Query q = em.createQuery(hql);
		q.setParameter("email", user.getEmail());
		q.setParameter("nickname", user.getNickname());
		q.setParameter("username", user.getUsername());
		q.executeUpdate();
	}

	protected void deleteUser(String userId) {
		
	}

	protected boolean userExists(String username) {
		TypedQuery<Integer> q = em.createQuery("SELECT 1 FROM User WHERE username = :username", Integer.class);
		List<Integer> result = q.getResultList();
		return CollectionUtils.isNotEmpty(result);
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

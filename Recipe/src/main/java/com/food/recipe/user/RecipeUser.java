package com.food.recipe.user;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RecipeUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int userId;
	private String nickname;
	private String email;
	private String phone;
	private LocalDateTime registerDate;
	
	public RecipeUser(int userId, String username, String nickname, String password,
			String email, String phone, LocalDateTime registerDate, 
			boolean enable, Collection<? extends GrantedAuthority> authorities) {
//		super(username, password, AuthorityUtils.NO_AUTHORITIES);
		super(username, password, enable,
				true, true, true,
				authorities);
		this.userId = userId;
		this.nickname = nickname;
		this.registerDate = registerDate;
		this.email = email;
		this.phone = phone;
	}

}

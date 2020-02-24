package com.food.recipe.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.food.recipe.user.entity.User;
import com.food.recipe.user.entity.UserGrantedAuthority;
import com.food.recipe.utils.Constants;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class RecipeUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User u = userRepo.getUserById(username);
			Collection<UserGrantedAuthority> role = userRepo.getAuthById(username);
			Collection<GrantedAuthority> grantedAuth = role.stream()
					.map(g -> new SimpleGrantedAuthority(g.getRole()))
					.collect(Collectors.toCollection(ArrayList::new));
			return entityToBean(u, grantedAuth);
		} catch (Exception e) {
			throw new UsernameNotFoundException(username + " was not found.");
		}
	}

	public void createUser(User user) {
		
		//validate qrcode
		LocalDateTime now = LocalDateTime.now();
		user.setRegisterDate(now);
		user.setEnable(true);
		User saved = userRepo.createUser(user);
		
		saveRoleForNewUser(now, saved);
	}

	private void saveRoleForNewUser(LocalDateTime now, User saved) {
		UserGrantedAuthority role = UserGrantedAuthority.builder()
				.role(Constants.ROLE_USER)
				.userId(saved.getUserId())
				.grantedDate(now)
				.build();
		userRepo.saveRole(role);
	}
	
	public void createUser(RecipeUser user) {
		LocalDateTime now = LocalDateTime.now();
		User u = beanToEntity(user);
		u.setRegisterDate(now);
		userRepo.createUser(u);
		saveRoleForNewUser(now, u);
	}

	public void changePassword(String oldPassword, String newPassword) {
		Authentication currentUser = SecurityContextHolder.getContext()
				.getAuthentication();
		if (currentUser == null) {
			// This would indicate bad coding somewhere
			throw new AccessDeniedException(
					"Can't change password as no Authentication object found in context "
							+ "for current user.");
		}
		String username = currentUser.getName();
		if (!userExists(username)) {
			throw new IllegalStateException("Current user doesn't exist in database.");
		}

		log.debug("Changing password for user '" + username + "'");

		if (authenticationManager != null) {
			log.debug("Reauthenticating user '" + username
					+ "' for password change request.");

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					username, oldPassword));
		}else {
			log.debug("No authentication manager set. Password won't be re-checked.");
		}

		userRepo.changePassword(username, newPassword);
	}
	
	public void updateUser(RecipeUser user) {
		User u = beanToEntity(user);
		userRepo.updateUser(u);
	}

	public void deleteUser(String username) {
		userRepo.deleteUser(username);
	}
	
	public boolean userExists(String username) {
		return userRepo.userExists(username);
	}
	
	private static User beanToEntity(RecipeUser userBean) {
		return User.builder().email(userBean.getEmail())
			.password(userBean.getPassword())
			.phone(userBean.getPhone())
			.registerDate(userBean.getRegisterDate())
			.userId(userBean.getUserId())
			.username(userBean.getUsername())
			.build();
	}

	private static RecipeUser entityToBean(User user, Collection<GrantedAuthority> role) {
		return new RecipeUser(user.getUserId(), user.getUsername(), user.getPassword(), 
				user.getEmail(), user.getPhone(), user.getRegisterDate(), user.isEnable(), role);
	}

}

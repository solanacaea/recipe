package com.food.recipe.user;

import com.food.recipe.register.RegisterMessage;
import com.food.recipe.register.RegisterMessageRepo;
import com.food.recipe.user.entity.User;
import com.food.recipe.user.entity.UserGrantedAuthority;
import com.food.recipe.utils.Constants;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@CommonsLog
@Transactional
public class RecipeUserDetailsService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RegisterMessageRepo resigterRepo;
	
	@Override
	public RecipeUser loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User u = userRepo.findByUsername(username);
			Collection<GrantedAuthority> grantedAuth = getGrantedAuthorities(username);
			return entityToBean(u, grantedAuth);
		} catch (Exception e) {
			throw new UsernameNotFoundException(username + " was not found.");
		}
	}

	private Collection<GrantedAuthority> getGrantedAuthorities(String username) {
		Collection<UserGrantedAuthority> role = userRepo.getAuthByName(username);
		return role.stream()
				.map(g -> new SimpleGrantedAuthority(g.getRole()))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public void createUser(RegisterUser user) {
		LocalDateTime now = LocalDateTime.now();
		//validate qrcode
		if (user.getCaptcha() == null) {
			throw new RuntimeException("请输入验证码！");
		}

		RegisterMessage msg = resigterRepo.getMessage(user.getPhone());
		if (msg == null) {
			throw new RuntimeException("请获取验证码！");
		}

		if (msg.getCreatedDate().plusSeconds(300).isBefore(now)) {
			throw new RuntimeException("验证码已过期！");
		}
		
		if (!StringUtils.equals(user.getCaptcha(), msg.getCode())) {
			throw new RuntimeException("请输入正确的验证码！");
		}

		User dbUser = User.builder().enable(true)
				.email(user.getEmail())
				.password(passwordEncoder.encode(user.getPassword()))
				.registerDate(now)
				.phone(user.getPhone())
				.nickname(user.getNickname())
				.username(user.getUsername())
				.build();

		User saved = userRepo.createUser(dbUser);
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

	public RecipeUser findByUserId(String userId) {
		User user = userRepo.findByUserId(userId);
		Collection<GrantedAuthority> grantedAuth = getGrantedAuthorities(user.getUsername());
		return entityToBean(user, grantedAuth);
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
		return new RecipeUser(user.getUserId(), user.getUsername(), user.getNickname(), user.getPassword(),
				user.getEmail(), user.getPhone(), user.getRegisterDate(), user.isEnable(), role);
	}

}

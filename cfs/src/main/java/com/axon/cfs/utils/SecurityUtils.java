package com.axon.cfs.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.axon.cfs.model.AbstractEntity;
import com.axon.cfs.model.UserModel;

public final class SecurityUtils {
	private SecurityUtils() {
	}

	/**
	 * Get the login of the current user.
	 *
	 * @return the login of the current user.
	 */
	public static Optional<String> getCurrentUserLogin() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		return Optional.ofNullable(securityContext.getAuthentication()).map(authentication -> {
			if (authentication.getPrincipal() instanceof UserDetails) {
				UserDetails user = (UserDetails) authentication.getPrincipal();
				return user.getUsername();
			}  else if (authentication.getPrincipal() instanceof UserModel) {
				UserModel springSecurityUser = (UserModel) authentication.getPrincipal();
				return springSecurityUser.getUserName();
			} else if (authentication.getPrincipal() instanceof String) {
				return (String) authentication.getPrincipal();
			}
			return null;
		});
	}

	/**
	 * Get the JWT of the current user.
	 *
	 * @return the JWT of the current user.
	 */
	public static Optional<String> getCurrentUserJWT() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		return Optional.ofNullable(securityContext.getAuthentication())
				.filter(authentication -> authentication.getCredentials() instanceof String)
				.map(authentication -> (String) authentication.getCredentials());
	}

	/**
	 * If the current user has a specific authority (security role).
	 * <p>
	 * The name of this method comes from the {@code isUserInRole()} method in the
	 * Servlet API.
	 *
	 * @param authority the authority to check.
	 * @return true if the current user has the authority, false otherwise.
	 */
	public static boolean isCurrentUserInRole(String authority) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		return Optional.ofNullable(securityContext.getAuthentication()).map(authentication -> {
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.addAll(authentication.getAuthorities());
			return authorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
		}).orElse(false);
	}

	public static void saveAbtractEntity(Object ob) {
		if (ob instanceof AbstractEntity) {
			String ntid = SecurityUtils.getCurrentUserLogin().orElse("");
			if (((AbstractEntity) ob).getCreatedBy() == null) {
				((AbstractEntity) ob).setCreatedBy(ntid);
			}
			((AbstractEntity) ob).setLastModifiedBy(ntid);
		}
	}
}

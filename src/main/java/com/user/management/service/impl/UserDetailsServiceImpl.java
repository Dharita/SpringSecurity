package com.user.management.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.user.management.bean.Role;
import com.user.management.bean.User;
import com.user.management.service.RoleService;
import com.user.management.service.UserService;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Override
	public UserDetails loadUserByUsername(final String login) {
		LOGGER.info("loadUserByUsername(): {}", login);
		User userFromDatabase = userService.getUserByUsername(login);

		if (null == userFromDatabase) {
			throw new UsernameNotFoundException("User " + login + " was not found in the database");
		}

		Role role = roleService.getRoleById(userFromDatabase.getRoleId());
		String roleType = (null != role && null != role.getName()) ? role.getName() : "ROLE_USER";
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleType);
		grantedAuthorities.add(grantedAuthority);

		return new org.springframework.security.core.userdetails.User(userFromDatabase.getUsername(), userFromDatabase.getPassword(), true, true, true, true,
				grantedAuthorities);
	}

}
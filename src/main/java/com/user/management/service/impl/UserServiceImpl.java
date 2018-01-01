/**
 * 
 */
package com.user.management.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.management.bean.User;
import com.user.management.dao.UserDao;
import com.user.management.service.UserService;

/**
 * @author dharita.chokshi
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public boolean saveOrUpdate(User user) {
		LOGGER.info("saveOrUpdate: {}", user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if (null == user.getId()) {
			return userDao.createUser(user);
		} else {
			return userDao.updateUser(user, user.getId());
		}
	}

	@Override
	public List<User> getUsers() {
		LOGGER.info("getUsers");
		return userDao.getUsers();
	}

	@Override
	public User getUserById(int userId) {
		LOGGER.info("getUserById: {}", userId);
		return userDao.getUserById(userId);
	}

	@Override
	public boolean deleteUser(int userId) {
		LOGGER.info("deleteUser: {}", userId);
		return userDao.deleteUser(userId);
	}

	@Override
	public User getUserByUsername(String username) {
		LOGGER.info("getUserByUsername: {}", username);
		return userDao.getUserByUsername(username);
	}

}
/**
 * 
 */
package com.user.management.service;

import java.util.List;

import com.user.management.bean.User;

/**
 * @author dharita.chokshi
 *
 */
public interface UserService {

	public boolean saveOrUpdate(User user);

	public List<User> getUsers();

	public User getUserById(int userId);

	public boolean deleteUser(int userId);

	public User getUserByUsername(String email);

}

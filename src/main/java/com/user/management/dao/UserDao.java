/**
 * 
 */
package com.user.management.dao;

import java.util.List;

import com.user.management.bean.User;

/**
 * @author dharita.chokshi
 *
 */
public interface UserDao {

	public boolean createUser(User user);

	public List<User> getUsers();

	public User getUserById(int userId);

	public boolean updateUser(User user, int userId);

	public boolean deleteUser(int userId);
	
	public User getUserByUsername(String email);

}

/**
 * 
 */
package com.user.management.dao;

import java.util.List;

import com.user.management.bean.Role;

/**
 * @author dharita.chokshi
 *
 */
public interface RoleDao {

	public Role getRoleByRoleName(String roleName);

	public Role getRoleById(Integer roleId);
	
	public List<Role> getRoles();

}

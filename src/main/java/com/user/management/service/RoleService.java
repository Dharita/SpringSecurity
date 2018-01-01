/**
 * 
 */
package com.user.management.service;

import java.util.List;

import com.user.management.bean.Role;

/**
 * @author dharita.chokshi
 *
 */
public interface RoleService {

	public Role getRoleByRoleName(String roleName);

	public Role getRoleById(Integer roleId);

	public List<Role> getRoles();

}

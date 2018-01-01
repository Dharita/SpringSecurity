/**
 * 
 */
package com.user.management.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.management.bean.Role;
import com.user.management.dao.RoleDao;
import com.user.management.service.RoleService;

/**
 * @author dharita.chokshi
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleDao roleDao;

	@Override
	public Role getRoleByRoleName(String roleName) {
		LOGGER.info("getRoleByRoleName: {}", roleName);
		return roleDao.getRoleByRoleName(roleName);
	}

	@Override
	public Role getRoleById(Integer roleId) {
		LOGGER.info("getRoleById: {}", roleId);
		return roleDao.getRoleById(roleId);
	}

	@Override
	public List<Role> getRoles() {
		LOGGER.info("getRoles");
		return roleDao.getRoles();
	}

}

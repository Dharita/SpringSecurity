/**
 * 
 */
package com.user.management.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.user.management.bean.Role;
import com.user.management.dao.RoleDao;

/**
 * @author dharita.chokshi
 *
 */
@Repository
@Qualifier("roleDao")
public class RoleDaoImpl implements RoleDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleDaoImpl.class);

	private String sqlQuery;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	/**
	 * Return role object for given roleName from database. If role is not found
	 * for requested roleName, return null.
	 * 
	 * @param roleName
	 * @return {@link Role}
	 */
	@Override
	public Role getRoleByRoleName(String roleName) {
		Role role = null;
		Map<String, Object> params = new HashMap<>();
		params.put("roleName", roleName);
		sqlQuery = "SELECT * FROM tbl_roles WHERE name=:roleName";
		try {
			role = namedParameterJdbcTemplate.queryForObject(sqlQuery, params, new RoleMapper());
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		LOGGER.info("Role: {} ", role);
		return role;
	}

	/**
	 * Return role object for given roleId from database. If role is not found
	 * for requested roleId, return null.
	 * 
	 * @param roleId
	 * @return {@link Role}
	 */
	@Override
	public Role getRoleById(Integer roleId) {
		Role role = null;
		Map<String, Object> params = new HashMap<>();
		params.put("id", roleId);
		sqlQuery = "SELECT * FROM tbl_roles WHERE id=:id";
		try {
			role = namedParameterJdbcTemplate.queryForObject(sqlQuery, params, new RoleMapper());
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		LOGGER.info("Role: {} ", role);
		return role;
	}

	/**
	 * Return list of role from database if found else return empty list.
	 * 
	 * @return {@link List} of {@link Role}
	 */
	@Override
	public List<Role> getRoles() {
		sqlQuery = "SELECT * FROM tbl_roles";
		return namedParameterJdbcTemplate.query(sqlQuery, new RoleMapper());
	}

	private static final class RoleMapper implements RowMapper<Role> {
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			Role role = new Role();
			role.setId(rs.getInt("id"));
			role.setName(rs.getString("name"));
			return role;
		}
	}

}
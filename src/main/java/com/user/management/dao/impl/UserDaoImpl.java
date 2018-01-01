/**
 * 
 */
package com.user.management.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.user.management.bean.User;
import com.user.management.dao.UserDao;

/**
 * @author dharita.chokshi
 *
 */
@Repository
@Qualifier("userDao")
public class UserDaoImpl implements UserDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

	private String sqlQuery;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	/**
	 * Create new user in database and return true if requested user is added
	 * successfully into database else return false .
	 * 
	 * @param user
	 * @return {@link Boolean}
	 */
	@Override
	public boolean createUser(User user) {
		LOGGER.info("createUser: {}", user);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		sqlQuery = "INSERT INTO tbl_users(NAME, EMAIL, ADDRESS, PASSWORD, NEWSLETTER, FRAMEWORK, GENDER, CONTACT_NUMBER, COUNTRY, SKILL) "
				+ "VALUES ( :name, :email, :address, :password, :newsletter, :framework, :gender, :contact_number, :country, :skill)";

		int row = namedParameterJdbcTemplate.update(sqlQuery, getSqlParameterByModel(user), keyHolder);
		if (row == 1) {
			user.setId(keyHolder.getKey().intValue());
			LOGGER.info("User Added!!");
			return true;
		} else {
			LOGGER.info("Error adding user");
			return false;
		}
	}

	/**
	 * Return list of users from database if found else return empty list.
	 * 
	 * @return {@link List} of {@link User}
	 */
	@Override
	public List<User> getUsers() {
		sqlQuery = "SELECT * FROM tbl_users";
		return namedParameterJdbcTemplate.query(sqlQuery, new UserMapper());
	}

	/**
	 * Return user for given id from database. If user is not found for
	 * requested userId, return null.
	 * 
	 * @param userId
	 * @return {@link User}
	 */
	@Override
	public User getUserById(int userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", userId);
		sqlQuery = "SELECT * FROM tbl_users WHERE id=:id";
		User result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sqlQuery, params, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		return result;
	}

	/**
	 * Update user for given userId in database. If user does not exist, return
	 * null.
	 * 
	 * @param user
	 * @param userId
	 * @return {@link User}
	 */
	@Override
	public boolean updateUser(User user, int userId) {
		sqlQuery = "UPDATE tbl_users SET NAME=:name, EMAIL=:email, ADDRESS=:address, "
				+ "PASSWORD=:password, NEWSLETTER=:newsletter, FRAMEWORK=:framework, "
				+ "GENDER=:gender, CONTACT_NUMBER=:contact_number, COUNTRY=:country, SKILL=:skill WHERE id=:id";

		int row = namedParameterJdbcTemplate.update(sqlQuery, getSqlParameterByModel(user));

		if (row == 1) {
			LOGGER.info("User update!!");
			return true;
		} else {
			LOGGER.info("Error updating user");
			return false;
		}
	}

	/**
	 * Delete the user object from database based on userID. If user is not
	 * found for requested userId, returns null.
	 * 
	 * @param userId
	 * @return {@link Boolean}
	 */
	@Override
	public boolean deleteUser(int userId) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		sqlQuery = "DELETE FROM tbl_users WHERE id= :id";
		int row = namedParameterJdbcTemplate.update(sqlQuery, new MapSqlParameterSource("id", userId), keyHolder);
		if (row == 1) {
			keyHolder.getKeys();
			LOGGER.info("User Deleted!!");
			return true;
		} else {
			LOGGER.info("Error deleting user");
			return false;
		}
	}

	/**
	 * Return user object based on username from database. If User is not found
	 * for requested username, return null.
	 * 
	 * @param username
	 * @return {@link User}
	 */
	@Override
	public User getUserByUsername(String username) {
		LOGGER.info("getUserByUsername: {}", username);

		Map<String, Object> params = new HashMap<>();
		params.put("username", username);

		sqlQuery = "SELECT * FROM tbl_users WHERE username=:username";

		User result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sqlQuery, params, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("EmptyResultDataAccessException occured");
		}
		LOGGER.error("getUserByUsername: {}", result);
		return result;
	}

	/**
	 * Unable to handle List<String> or Array BeanPropertySqlParameterSource
	 * 
	 * @param user
	 * @return {@link SqlParameterSource}
	 */
	private SqlParameterSource getSqlParameterByModel(User user) {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", user.getId());
		paramSource.addValue("name", user.getName());
		paramSource.addValue("email", user.getEmail());
		paramSource.addValue("username", user.getUsername());
		paramSource.addValue("password", user.getPassword());
		paramSource.addValue("gender", user.getGender());
		paramSource.addValue("contact_number", user.getContactNumber());
		paramSource.addValue("address", user.getAddress());
		paramSource.addValue("country", user.getCountry());
		paramSource.addValue("newsletter", user.isNewsletter());
		paramSource.addValue("framework", convertListToDelimitedString(user.getFramework()));
		paramSource.addValue("skill", convertListToDelimitedString(user.getSkill()));
		paramSource.addValue("role_id", user.getRoleId());

		return paramSource;
	}

	private static final class UserMapper implements RowMapper<User> {

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setGender(rs.getString("gender"));
			user.setContactNumber(rs.getLong("contact_number"));
			user.setAddress(rs.getString("address"));
			user.setCountry(rs.getString("country"));
			user.setNewsletter(rs.getBoolean("newsletter"));
			user.setFramework(convertDelimitedStringToList(rs.getString("framework")));
			user.setSkill(convertDelimitedStringToList(rs.getString("skill")));
			user.setRoleId(rs.getInt("role_id"));
			return user;
		}

		private static List<String> convertDelimitedStringToList(String delimitedString) {
			List<String> result = new ArrayList<>();
			if (!StringUtils.isEmpty(delimitedString)) {
				result = Arrays.asList(StringUtils.delimitedListToStringArray(delimitedString, ","));
			}
			return result;
		}
	}

	private String convertListToDelimitedString(List<String> list) {
		String result = "";
		if (null != list) {
			result = StringUtils.arrayToCommaDelimitedString(list.toArray());
		}
		return result;
	}

}
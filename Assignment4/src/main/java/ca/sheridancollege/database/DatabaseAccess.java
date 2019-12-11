package ca.sheridancollege.database;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.User;

@Repository
public class DatabaseAccess {
	
	@Autowired
	NamedParameterJdbcTemplate jdbc;
	
	public ArrayList<User> getUsers() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM user";
		ArrayList<User> users = (ArrayList<User>)jdbc.query(query, parameters, 
				new BeanPropertyRowMapper<User>(User.class));
		return users;
	}
	
	public User getUserById(int userId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM user WHERE userId = :userId";
		parameters.addValue("userId", userId);
		ArrayList<User> users = (ArrayList<User>)jdbc.query(query, parameters, 
				new BeanPropertyRowMapper<User>(User.class));
		if(users.size() > 0)
			return users.get(0);
		return null;
		
	}

}

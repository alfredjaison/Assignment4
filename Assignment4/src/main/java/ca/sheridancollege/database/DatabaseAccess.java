package ca.sheridancollege.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.Phone;
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
	
	public void addUser(User user) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSER INTO user(name, email, encryptedPassword) "
				+ "VALUES(:name, :email, :encryptedPassword)";
		parameters.addValue("name", user.getName());
		parameters.addValue("email", user.getEmail());
		parameters.addValue("encryptedPassword", user.getEncryptedPassword());
		jdbc.update(query, parameters);
	}
	
	public void addPhone(Phone phone) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSER INTO phone(manufacturer, model, price, screnSize, battery, ram, storage, processor, dimensions, waterProofRating) "
				+ "VALUES(:manufacturer, :model, :price, :screnSize, :battery, :ram, :storage, :processor, :dimensions, :waterProofRating)";
		parameters.addValue("manufacturer", phone.getManufacturer());
		parameters.addValue("model", phone.getModel());
		parameters.addValue("price", phone.getPrice());
		parameters.addValue("screenSize", phone.getScreenSize());
		parameters.addValue("battery", phone.getBattery());
		parameters.addValue("ram", phone.getRam());
		parameters.addValue("storage", phone.getStorage());
		parameters.addValue("processor", phone.getProcessor());
		parameters.addValue("dimensions", phone.getDimensions());
		parameters.addValue("waterProofRating", phone.getWaterProofRating());
		jdbc.update(query, parameters);
	}
	
	public ArrayList<String> getRolesById(int userId){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		ArrayList<String> roles = new ArrayList<String>();
		String query = "SELECT * FROM role r "
				+ "JOIN user_role ur ON r.roleId = ur.roleId"
				+ "JOIN user u ON ur.userId = u.userId "
				+ "WHERE userId = :userId";
		parameters.addValue("userId", userId);
		List<Map<String,Object>> rows = jdbc.queryForList(query, parameters);
		for(Map<String,Object> row :rows) {
			roles.add((String)row.get("roleName"));
		}
		return roles;
	}

}

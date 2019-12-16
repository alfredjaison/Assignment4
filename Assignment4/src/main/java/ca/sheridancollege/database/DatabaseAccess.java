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
		String query = "INSERT INTO user(userName, email, encryptedPassword) "
				+ "VALUES(:userName, :email, :encryptedPassword)";
		parameters.addValue("userName", user.getUserName());
		parameters.addValue("email", user.getEmail());
		parameters.addValue("encryptedPassword", user.getEncryptedPassword());
		jdbc.update(query, parameters);
	}
	
	public ArrayList<String> getRolesById(int userId){
		System.out.println(userId);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		ArrayList<String> roles = new ArrayList<String>();
		String query = "SELECT u.userId, r.roleName FROM user_role u "
				+ "JOIN role r ON r.roleId = u.roleId "
				+ "WHERE userId = :userId";
		parameters.addValue("userId", userId);
		List<Map<String,Object>> rows = jdbc.queryForList(query, parameters);
		for(Map<String,Object> row :rows) {
			roles.add((String)row.get("roleName"));
		}
		return roles;
	}
	
	public void addRole(long userId, long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into user_role (userId, roleId) values (:userId, :roleId)";
		parameters.addValue("userId", userId);
		parameters.addValue("roleId", roleId);
		jdbc.update(query,  parameters);
	}
	
	public ArrayList<Phone> getPhones() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM phone ORDER BY dateOfRelease";
		ArrayList<Phone> phones = (ArrayList<Phone>)jdbc.query(query, parameters, 
				new BeanPropertyRowMapper<Phone>(Phone.class));
		return phones;
	}
	
	public Phone getPhoneById(int phoneId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM phone WHERE phoneId = :phoneId";
		parameters.addValue("phoneId", phoneId);
		ArrayList<Phone> phones = (ArrayList<Phone>)jdbc.query(query, parameters, 
				new BeanPropertyRowMapper<Phone>(Phone.class));
		if(phones.size() > 0)
			return phones.get(0);
		return null;
	}
	
	public ArrayList<Phone> getPhonesBy(String string, String choice) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM phone WHERE " + choice +"= :string";
		parameters.addValue("string", string);
		System.out.println(string + ", " + choice);
		ArrayList<Phone> phones = (ArrayList<Phone>)jdbc.query(query, parameters, 
				new BeanPropertyRowMapper<Phone>(Phone.class));
			return phones;
	}
	
	public ArrayList<Phone> getPhonesByLike(String string, String choice) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM phone WHERE " + choice +" LIKE :string";
		parameters.addValue("string", "%" + string + "%");
		
		System.out.println(string + ", " + choice);
		ArrayList<Phone> phones = (ArrayList<Phone>)jdbc.query(query, parameters, 
				new BeanPropertyRowMapper<Phone>(Phone.class));
			return phones;
	}
	public ArrayList<Phone> getPhonesByRange(double string, String choice) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM phone WHERE " + choice + " BETWEEN :lowNum AND :highNum";
		double highNum = string + (string * 0.1);
		double lowNum = string - (string * 0.1);
		parameters.addValue("highNum","" + highNum);
		parameters.addValue("lowNum", "" + lowNum);
		
		System.out.println(string + ", " + choice + ", " + highNum + ", " + lowNum);
		ArrayList<Phone> phones = (ArrayList<Phone>)jdbc.query(query, parameters, 
				new BeanPropertyRowMapper<Phone>(Phone.class));
			return phones;
	}
	
	public void addPhone(Phone phone) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO phone(manufacturer, model, price, screenSize, battery, ram, storage, processor, dimensions, waterProofRating, dateOfRelease) "
				+ "VALUES(:manufacturer, :model, :price, :screenSize, :battery, :ram, :storage, :processor, :dimensions, :waterProofRating, :dateOfRelease)";
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
		parameters.addValue("dateOfRelease", phone.getDateOfRelease());
		jdbc.update(query, parameters);
	}
	
	public void updatePhone(Phone phone) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "UPDATE phone SET manufacturer=:manufacturer, model=:model, price=:price, screenSize=:screenSize, "
				+ "battery=:battery, ram=:ram, storage=:storage, processor=:processor, dimensions=:dimensions, "
				+ "waterProofRating=:waterProofRating, dateOfRelease=:dateOfRelease WHERE phoneId=:phoneId";
		
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
		parameters.addValue("dateOfRelease", phone.getDateOfRelease());
		parameters.addValue("phoneId", phone.getPhoneId());
		
		jdbc.update(query, parameters);
	}
	
	public void deletePhone(int phoneId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "DELETE FROM phone WHERE phoneId=:phoneId";
		parameters.addValue("phoneId", phoneId);
		jdbc.update(query, parameters);
	}
	
	

	public User findUserAccount(String userName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM user WHERE userName = :username";
		parameters.addValue("username", userName);
		ArrayList<User> users = (ArrayList<User>)jdbc.query(query, parameters, 
				new BeanPropertyRowMapper<User>(User.class));
		if(users.size() > 0)
			return users.get(0);
		return null;
	}

	

}

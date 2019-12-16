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
	
	public void createDummyRecords() {
		addPhone(new Phone("LG", "G4", 399.0, "5.5", 3000, 3, 32, "1.4GHz hexa-core Qualcomm Snapdragon 808", "148 x 76 x 6", "N/A", "2015-04-01"));
		addPhone(new Phone("Apple", "iPhone 7 Plus", 899.0, "5.5", 2900, 3, 256, "2.34GHz Quad-core Apple A10 Fusion", "158 x 78 x 7", "IP67", "2016-09-01"));
		addPhone(new Phone("Samsung", "Galaxy Note 8", 499.0, "6.3", 3300, 6, 256, "2.3GHz Octa-core Qualcomm Snapdragon 835", "162 x 74 x 9", "IP68", "2017-09-01"));
		addPhone(new Phone("Google", "Pixel 3", 799.0, "5.5", 2915, 4, 128, "2.5GHz Octa-core Qualcomm Snapdragon 845", "145 x 68 x 8", "IP68", "2018-11-01"));
		addPhone(new Phone("Samsung", "Galaxy A70", 499.0, "6.7", 4500, 6, 128, "2GHz octa-core Qualcomm Snapdragon 675", "164 x 76 x 7", "N/A", "2019-04-01"));
		addPhone(new Phone("Apple", "iPhone 11 Pro Max", 1099.99, "6.5", 3969, 4, 512, "2GHz hexa-core Apple A13 Bionic", "158 x 77 x 8", "IP68", "2019-09-01"));
		addPhone(new Phone("Huawei", "Mate 30 Pro", 1200.0, "6.53", 4500, 8, 256, "2.86GHz Octa-core HiSilicon Kirin 990", "158 x 73 x 9", "IP68", "2019-09-01"));
		addPhone(new Phone("OnePlus", "7T", 799.0, "6.55", 3800, 8, 256, "2.96GHz Octa-core Qualcomm Snapdragon 855+", "160 x 74 x 8", "N/A", "2019-10-01"));
	}
	
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

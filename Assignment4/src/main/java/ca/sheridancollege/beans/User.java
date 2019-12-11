package ca.sheridancollege.beans;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements java.io.Serializable{
	private int userId;
	private String name;
	private String email;
	private String encryptedPassword;

}
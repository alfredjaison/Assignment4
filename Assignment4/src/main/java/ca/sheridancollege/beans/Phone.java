package ca.sheridancollege.beans;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Phone implements java.io.Serializable{
	private String manufacturer;
	private String model;
	private double price;
	private String screenSize;
	private int battery;
	private int ram;
	private int storage;
	private String processor;
	private String dimensions;
	private String waterProofRating;
	
	private String[] manufacturers = {"Apple", "Samsung", "Google", "LG", "OnePlus", "Huawei"};
	

}

package ca.sheridancollege.beans;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Phone implements java.io.Serializable{
	private int phoneId;
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
	private String dateOfRelease;
	
	private String[] manufacturers = {"Apple", "Samsung", "Google", "LG", "OnePlus", "Huawei"};
	
	public Phone(String manufacturer, String model, double price, String screenSize, int battery, 
			int ram, int storage, String processor, String dimensions, String waterProofRating, String dateOfRelease) {
		
		this.manufacturer = manufacturer;
		this.model = model;
		this.price = price;
		this.screenSize = screenSize;
		this.battery = battery;
		this.ram = ram;
		this.storage = storage;
		this.processor = processor;
		this.dimensions = dimensions;
		this.waterProofRating = waterProofRating;
		this.dateOfRelease = dateOfRelease;
	}
	

}

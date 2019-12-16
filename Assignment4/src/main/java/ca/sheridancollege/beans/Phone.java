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
	
	public int equalsSpecific(Phone phone) {
		
		boolean flagList[] = new boolean[11];
		int matchCount = 0;
		
		flagList[0] = phone.getManufacturer().equals(this.manufacturer);
		flagList[1] = phone.getModel().equals(this.model);
		flagList[2] = phone.getPrice() == this.price;
		flagList[3] = phone.getScreenSize().equals(this.screenSize);
		flagList[4] = phone.getBattery() == this.battery;
		flagList[5] = phone.getRam() == this.ram;
		flagList[6] = phone.getStorage() == this.storage;
		flagList[7] = phone.getProcessor().equals(this.processor);
		flagList[8] = phone.getDimensions().equals(this.dimensions);
		flagList[9] = phone.getWaterProofRating().equals(this.waterProofRating);
		flagList[10] = phone.getDateOfRelease().equals(this.dateOfRelease);
		
		for(boolean flag : flagList) {
			if(flag)
				matchCount++;
		}
		
		return matchCount;
	}
	

}

package util;

public class PatientPerMaterial {
	  private String ID ;
	  private String FirstName ;
	  private String LastName ;
	  private float Price ;
	  private String Material;
	public PatientPerMaterial(String iD, String firstName, String lastName, float price, String material) {
		ID = iD;
		FirstName = firstName;
		LastName = lastName;
		Price = price;
		Material = material;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public float getPrice() {
		return Price;
	}
	public void setPrice(float price) {
		Price = price;
	}
	public String getMaterial() {
		return Material;
	}
	public void setMaterial(String material) {
		Material = material;
	}
}

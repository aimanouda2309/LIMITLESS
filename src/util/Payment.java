package util;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

public class Payment {
	  private String ID ;
	  private String FirstName ;
	  private String LastName;
	  private java.sql.Timestamp date ;	 
	  private String Material;
	  private String Teacher;
	  private float Hours ;
	  private float Minutes ;
	  private float Price ;
	  private float Pay ;
	  private String Type;
	 public String getTeacher() {
		return Teacher;
	}


	public void setTeacher(String teacher) {
		Teacher = teacher;
	}


	public String getType() {
		return Type;
	}


	public void setType(String type) {
		Type = type;
	}


	private String Final;

	    public Payment(String ID, String FirstName, String LastName, java.sql.Timestamp date,String Material,float Hours,float Minutes,float Price,float Pay,String Final,String Teacher, String Type){
	        
	        this.ID=ID;
	        this.FirstName=FirstName;
	        this.date=date;
	        this.LastName=LastName;
	        this.Material=Material;
	        this.Hours=Hours;
	        this.Minutes=Minutes;
	        this.Pay=Pay;
	        this.Price=Price;
	        this.Final=Final;
	        this.Teacher=Teacher;
	        this.Type=Type;

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


		public java.sql.Timestamp getDate() {
			return date;
		}


		public void setDate(java.sql.Timestamp date) {
			this.date = date;
		}


		public float getPrice() {
			return Price;
		}


		public void setPrice(float price) {
			Price = price;
		}


		public String getMaterials() {
			return Material;
		}


		public void setMaterials(String materials) {
			Material = materials;
		}


		public float getHours() {
			return Hours;
		}


		public void setHours(float hours) {
			Hours = hours;
		}


		public float getMinutes() {
			return Minutes;
		}


		public void setMinutes(float minutes) {
			Minutes = minutes;
		}


		public float getPay() {
			return Pay;
		}


		public void setPay(float pay) {
			Pay = pay;
		}


		public String getFinal() {
			return Final;
		}


		public void setFinal(String final1) {
			Final = final1;
		}

		

}

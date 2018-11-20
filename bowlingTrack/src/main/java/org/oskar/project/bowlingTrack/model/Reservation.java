package org.oskar.project.bowlingTrack.model;


public class Reservation {

	private int number;
	private String firstName;
	private String lastName;
	private String date;	
	private int telephone;
	

	public Reservation(int number, String firstName, String lastName, String date, int telephone) {
		
		this.number = number;
		this.firstName = firstName;
		this.lastName = lastName;
		this.date = date;
		this.telephone = telephone;
		
	}
	
	public Reservation() {
		
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public int getTelephone() {
		return telephone;
	}


	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}


	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	
}

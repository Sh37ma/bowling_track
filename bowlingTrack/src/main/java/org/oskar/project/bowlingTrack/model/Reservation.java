package org.oskar.project.bowlingTrack.model;


public class Reservation {

	private String id;
	private long number;
	private String firstName;
	private String lastNameName;
	private String date;	
	private long telephone;
	

	public Reservation() {
		
	}


	public long getNumber() {
		return number;
	}


	public void setNumber(long number) {
		this.number = number;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}



	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public long getTelephone() {
		return telephone;
	}


	public void setTelephone(long telephone) {
		this.telephone = telephone;
	}


	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastNameName() {
		return lastNameName;
	}



	public void setLastNameName(String lastNameName) {
		this.lastNameName = lastNameName;
	}
	
	
	
}

package org.oskar.project.bowlingTrack.model;

import java.util.List;

import org.bson.types.ObjectId;

public class Customer {

	private ObjectId id;
	private String userName;
	private String password;
	private List<Integer> reservations;
	

	public Customer( String userName, String password, List<Integer> reservations) {
		
		this.userName = userName;
		this.password = password;
		this.reservations = reservations;
	}
	
	public Customer() {
		
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Integer> getReservations() {
		return reservations;
	}

	public void setReservations(List<Integer> reservations) {
		this.reservations = reservations;
	}

	
	
}

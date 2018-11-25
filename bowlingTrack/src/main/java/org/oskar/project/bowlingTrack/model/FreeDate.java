package org.oskar.project.bowlingTrack.model;

import java.util.List;

import org.bson.types.ObjectId;

public class FreeDate {
	
	private ObjectId id;
	private int weekNumber;
	private Boolean freeDates;
	private List<Boolean> daysFree;
	private List<Boolean> mondayHours;
	private List<Boolean> tuesdayHours;
	private List<Boolean> wednesdayHours;
	private List<Boolean> thursdayHours;
	private List<Boolean> fridayHours;
	private List<Boolean> saturdayHours;
	private List<Boolean> sundayHours;
	
	
	public FreeDate() {
		
	}
	
	
	public FreeDate(int weekNumber, boolean freeDates, List<Boolean> daysFree, List<Boolean> mondayHours,
			List<Boolean> tuesdayHours, List<Boolean> wednesdayHours, List<Boolean> thursdayHours,
			List<Boolean> fridayHours, List<Boolean> saturdayHours, List<Boolean> sundayHours) {
		
		this.weekNumber = weekNumber;
		this.freeDates = freeDates;
		this.daysFree = daysFree;
		this.mondayHours = mondayHours;
		this.tuesdayHours = tuesdayHours;
		this.wednesdayHours = wednesdayHours;
		this.thursdayHours = thursdayHours;
		this.fridayHours = fridayHours;
		this.saturdayHours = saturdayHours;
		this.sundayHours = sundayHours;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public int getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}
	public Boolean isFreeDates() {
		return freeDates;
	}
	public void setFreeDates(Boolean freeDates) {
		this.freeDates = freeDates;
	}
	public List<Boolean> getDaysFree() {
		return daysFree;
	}
	public void setDaysFree(List<Boolean> daysFree) {
		this.daysFree = daysFree;
	}
	public List<Boolean> getMondayHours() {
		return mondayHours;
	}
	public void setMondayHours(List<Boolean> mondayHours) {
		this.mondayHours = mondayHours;
	}
	public List<Boolean> getTuesdayHours() {
		return tuesdayHours;
	}
	public void setTuesdayHours(List<Boolean> tuesdayHours) {
		this.tuesdayHours = tuesdayHours;
	}
	public List<Boolean> getWednesdayHours() {
		return wednesdayHours;
	}
	public void setWednesdayHours(List<Boolean> wednesdayHours) {
		this.wednesdayHours = wednesdayHours;
	}
	public List<Boolean> getThursdayHours() {
		return thursdayHours;
	}
	public void setThursdayHours(List<Boolean> thursdayHours) {
		this.thursdayHours = thursdayHours;
	}
	public List<Boolean> getFridayHours() {
		return fridayHours;
	}
	public void setFridayHours(List<Boolean> fridayHours) {
		this.fridayHours = fridayHours;
	}
	public List<Boolean> getSaturdayHours() {
		return saturdayHours;
	}
	public void setSaturdayHours(List<Boolean> saturdayHours) {
		this.saturdayHours = saturdayHours;
	}
	public List<Boolean> getSundayHours() {
		return sundayHours;
	}
	public void setSundayHours(List<Boolean> sundayHours) {
		this.sundayHours = sundayHours;
	}
	
}



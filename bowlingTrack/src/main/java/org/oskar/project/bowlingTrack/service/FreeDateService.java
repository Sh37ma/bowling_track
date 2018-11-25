package org.oskar.project.bowlingTrack.service;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.oskar.project.bowlingTrack.database.MongoDBClass;
import org.oskar.project.bowlingTrack.exception.DataNotFoundException;
import org.oskar.project.bowlingTrack.model.FreeDate;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class FreeDateService {
	
	public enum DayOfTheWeek {
		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
	}
	
	private String collectionName = "freeDates";
	private MongoDBClass mongoDBClass = new MongoDBClass();
	private CodecRegistry pojoCodecRegistry = mongoDBClass.getPojoCodecRegistry();
	
	public FreeDateService() {
	}
	
	
	
	public List<FreeDate> getAllFreeDates(){
	      
		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		MongoCollection<FreeDate> col = database.getCollection(collectionName, FreeDate.class)
			   									   .withCodecRegistry(pojoCodecRegistry);
		List<FreeDate> list = new ArrayList<FreeDate>();
		
		try {
		    Block<FreeDate> addBlock = new Block<FreeDate>() {
		    	@Override
		    	public void apply(final FreeDate freedates) {
		    		list.add(freedates);
		    	}
		    };
		
		    col.find().forEach(addBlock);
		        
		}
		finally{
			mongoDBClass.closeConnection();
			
		}
		
		if(list.isEmpty()) {
			throw new DataNotFoundException("Collection is empty");
		}
		
		return list;		
	}
	
	public FreeDate getFreeDate(int weekNumber) {
		
		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		MongoCollection<FreeDate> col = database.getCollection(collectionName, FreeDate.class)
				   .withCodecRegistry(pojoCodecRegistry);
		FreeDate freeDate;
		
		try {
			freeDate = col.find(eq("weekNumber", weekNumber)).first();
			
		}
		finally{
			mongoDBClass.closeConnection();
			
		}
		
		if(freeDate == null) {
			throw new DataNotFoundException("FreeDate with weekNumber: " + weekNumber + " not found");
		}
		
		return freeDate;
	}
	
	
	public FreeDate addFreeDate(FreeDate newFreeDate) {
		
		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
				
		MongoCollection<FreeDate> col = database.getCollection(collectionName, FreeDate.class)
				   .withCodecRegistry(pojoCodecRegistry);
		    
		FreeDate freeDate= col.find().sort(new Document("weekNumber", -1)).first();	
		
		try {	    
			int newNumber = 0;
			
			if(freeDate != null){
				newNumber = freeDate.getWeekNumber();
			}
			
			newFreeDate.setWeekNumber(newNumber + 1);
			
			//Inserting new FreeDate		  
		    col.insertOne(newFreeDate);
		    
		} 
		finally{
			mongoDBClass.closeConnection();
		}
		 
		return newFreeDate;
	}
	
	public FreeDate updateEntireFreeDate(FreeDate freeDate, int weekNumber) {

		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		MongoCollection<FreeDate> col = database.getCollection(collectionName, FreeDate.class)
				   .withCodecRegistry(pojoCodecRegistry);
		FreeDate freeDateToChange = col.find(eq("weekNumber", weekNumber)).first();
		
		if(freeDateToChange == null) {
			throw new DataNotFoundException("FreeDate with weeknumber: " + weekNumber + " not found and not updated");
		}
		
		try {
			col.updateOne(eq("weekNumber", weekNumber),combine
					   (set("freeDates", freeDate.isFreeDates()), 
						set("daysFree", freeDate.getDaysFree()), 
						set("mondayHours", freeDate.getMondayHours()), 
						set("tuesdayHours", freeDate.getTuesdayHours()), 
						set("wednesdayHours", freeDate.getWednesdayHours()), 
						set("thursdayHours", freeDate.getThursdayHours()), 
						set("fridayHours", freeDate.getFridayHours()), 
						set("saturdayHours", freeDate.getSaturdayHours()), 
						set("sundayHours", freeDate.getSundayHours())));
			
			updateInfoWeek(col, freeDateToChange, weekNumber);
			
		}
		finally{ 
			mongoDBClass.closeConnection();
			
		}
		return freeDate;
	}
	
	public FreeDate updateDay(int weekNumber, FreeDate freeDate, String day) {
		
		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		MongoCollection<FreeDate> col = database.getCollection(collectionName, FreeDate.class)
				   .withCodecRegistry(pojoCodecRegistry);
		FreeDate freeDateToChange = col.find(eq("weekNumber", weekNumber)).first();
		
		if(freeDateToChange == null) {
			throw new DataNotFoundException("FreeDate with weeknumber: " + weekNumber + " not found and not updated");
		}
		
		day = day.toUpperCase();
		
		try {
			switch(DayOfTheWeek.valueOf(day)) {
			case MONDAY:
				col.updateOne(eq("weekNumber", weekNumber),combine
						   (set("mondayHours", freeDate.getMondayHours())));
				//get changed document from DB after changing one table, in http we send only one table, so we can't read rest from http
				freeDateToChange = col.find(eq("weekNumber", weekNumber)).first();
				updateInfoDay(col, freeDateToChange, weekNumber, 0, freeDateToChange.getMondayHours());
				break;
			case TUESDAY:
				col.updateOne(eq("weekNumber", weekNumber),combine
						   (set("tuesdayHours", freeDate.getTuesdayHours())));
				freeDateToChange = col.find(eq("weekNumber", weekNumber)).first();
				updateInfoDay(col, freeDateToChange, weekNumber, 1, freeDateToChange.getTuesdayHours());
				break;
			case WEDNESDAY:
				col.updateOne(eq("weekNumber", weekNumber),combine
						   (set("wednesdayHours", freeDate.getWednesdayHours())));
				freeDateToChange = col.find(eq("weekNumber", weekNumber)).first();
				updateInfoDay(col, freeDateToChange, weekNumber, 2, freeDateToChange.getWednesdayHours());
				break;
			case THURSDAY:
				col.updateOne(eq("weekNumber", weekNumber),combine
						   (set("thursdayHours", freeDate.getThursdayHours())));
				freeDateToChange = col.find(eq("weekNumber", weekNumber)).first();
				updateInfoDay(col, freeDateToChange, weekNumber, 3, freeDateToChange.getThursdayHours());
				break;
			case FRIDAY:
				col.updateOne(eq("weekNumber", weekNumber),combine
						   (set("fridayHours", freeDate.getFridayHours())));
				freeDateToChange = col.find(eq("weekNumber", weekNumber)).first();
				updateInfoDay(col, freeDateToChange, weekNumber, 4, freeDateToChange.getFridayHours());
				break;
			case SATURDAY:
				col.updateOne(eq("weekNumber", weekNumber),combine
						   (set("saturdayHours", freeDate.getSaturdayHours())));
				freeDateToChange = col.find(eq("weekNumber", weekNumber)).first();
				updateInfoDay(col, freeDateToChange, weekNumber, 5, freeDateToChange.getSaturdayHours());
				break;
			case SUNDAY:
				col.updateOne(eq("weekNumber", weekNumber),combine
						   (set("sundayHours", freeDate.getSundayHours())));
				freeDateToChange = col.find(eq("weekNumber", weekNumber)).first();
				updateInfoDay(col, freeDateToChange, weekNumber, 6, freeDateToChange.getSundayHours());
				break;			
			default:
				throw new DataNotFoundException("FreeDate with weeknumber: " + weekNumber + " not updated, incorrect day");
				
			}
			updateInfoWeek(col, freeDateToChange, weekNumber);
			
		}
		finally{
			mongoDBClass.closeConnection();
			
		}
		return freeDate;
		
	}
	
	private void updateInfoDay(MongoCollection<FreeDate> col, FreeDate freeDateToChange, int weekNumber, int dayOfTheWeek, List<Boolean> list) {
			
		
		//update info in "daysFree" table in DB
		int countTakenHours = 0;
		
		for(boolean b : list){
			
			if(b == false) {
				countTakenHours++;
			}
		}
		
		List<Boolean> newList = freeDateToChange.getDaysFree();
		
		//if necessary updating info
		if(countTakenHours >= 9 && freeDateToChange.getDaysFree().get(dayOfTheWeek)) {
			
			
			newList.set(dayOfTheWeek, false);
			
			col.updateOne(eq("weekNumber", weekNumber),combine
					   (set("daysFree", newList)));
		}
		else if(countTakenHours < 9 && !freeDateToChange.getDaysFree().get(dayOfTheWeek)) {
			
			newList.set(dayOfTheWeek, true);
			
			col.updateOne(eq("weekNumber", weekNumber),combine
					   (set("daysFree", newList)));
		}
		
	}
	
	
	private void updateInfoWeek(MongoCollection<FreeDate> col, FreeDate freeDateToChange, int weekNumber) {
		
		//update info about "freeDates" variable in DB
		int countTakenDays = 0;
		
		for(boolean b : freeDateToChange.getDaysFree()){
			
			if(b == false) {
				countTakenDays++;
			}
		}
		
		//if necessary updating info about free dates in week
		if(countTakenDays >= 7 && freeDateToChange.isFreeDates()) {
			
			col.updateOne(eq("weekNumber", weekNumber),combine
					   (set("freeDates", false)));
		}
		else if(countTakenDays < 7 && !freeDateToChange.isFreeDates()) {
			col.updateOne(eq("weekNumber", weekNumber),combine
					   (set("freeDates", true)));
		}
		
	}
	
	public FreeDate removeFreeDate(int weekNumber) {
		
		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		FreeDate freeDateToDelete;
		
		try {
			MongoCollection<FreeDate> col = database.getCollection(collectionName, FreeDate.class)
					   .withCodecRegistry(pojoCodecRegistry);
			freeDateToDelete= col.find(eq("weekNumber", weekNumber)).first();
			col.deleteOne(eq("weekNumber", weekNumber));;
			
		}
		finally{
			mongoDBClass.closeConnection();
			
		}
		
		if(freeDateToDelete == null) {
			throw new DataNotFoundException("FreeDate with weekNumber: " + weekNumber + " not found and not deleted");
		}
		
		return freeDateToDelete;
	}

}

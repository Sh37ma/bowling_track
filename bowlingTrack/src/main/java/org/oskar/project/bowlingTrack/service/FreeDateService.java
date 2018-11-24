package org.oskar.project.bowlingTrack.service;

import static com.mongodb.client.model.Filters.eq;

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
	
	public FreeDate updateFreeDate(FreeDate freeDate, int weekNumber) {

		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		FreeDate freeDateToChange;
		
		try {
			MongoCollection<FreeDate> col = database.getCollection(collectionName, FreeDate.class)
					   .withCodecRegistry(pojoCodecRegistry);
			freeDateToChange= col.find(eq("weekNumber", weekNumber)).first();
			
			//TODO much
			/*col.updateOne(eq("userName", userName),combine
						   (set("userName", customer.getUserName()), 
							set("password", customer.getPassword()), 
							set("reservations", customer.getReservations())));
		*/
		}
		finally{
			mongoDBClass.closeConnection();
			
		}
		
		if(freeDateToChange == null) {
			throw new DataNotFoundException("FreeDate with weeknumber: " + weekNumber + " not found and not updated");
		}
		
		return freeDate;
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

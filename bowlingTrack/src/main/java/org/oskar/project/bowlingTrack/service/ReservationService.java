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
import org.oskar.project.bowlingTrack.model.Reservation;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ReservationService {
	
	private String collectionName = "reservations";
	private MongoDBClass mongoDBClass = new MongoDBClass();
	private CodecRegistry pojoCodecRegistry = mongoDBClass.getPojoCodecRegistry();
	
	public ReservationService() {
	}
	
	
	public List<Reservation> getAllReservations(){
      
		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		MongoCollection<Reservation> col = database.getCollection(collectionName, Reservation.class)
			   									   .withCodecRegistry(pojoCodecRegistry);
		List<Reservation> list = new ArrayList<Reservation>();
		
		try {
		    Block<Reservation> addBlock = new Block<Reservation>() {
		    	@Override
		    	public void apply(final Reservation reservation) {
		    		list.add(reservation);
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
	
	
	public Reservation getReservation(long number) {
		
		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		MongoCollection<Reservation> col = database.getCollection(collectionName, Reservation.class)
				   .withCodecRegistry(pojoCodecRegistry);
		Reservation reservation;
		
		try {
			reservation = col.find(eq("number", number)).first();
			
		}
		finally{
			mongoDBClass.closeConnection();
			
		}
		
		if(reservation == null) {
			throw new DataNotFoundException("Reservation with number: " + number + " not found");
		}
		
		return reservation;
	}
	
	
	public Reservation addReservation(Reservation newReservation) {
		
		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
				
		MongoCollection<Reservation> col = database.getCollection(collectionName, Reservation.class)
				   .withCodecRegistry(pojoCodecRegistry);
		//get highest number of reservation in DB
		Reservation reservation= col.find().sort(new Document("number", -1)).first();		
		try {	    
			int newNumber = 0;
			
			if(reservation != null){
				newNumber = reservation.getNumber();
			}
			
	        newReservation.setNumber(newNumber + 1);
			
			//Inserting new Reservation		  
		    col.insertOne(newReservation);
		} 
		finally{
			
			mongoDBClass.closeConnection();
		}
		 
		return newReservation;
	}
	
	
	public Reservation updateReservation(Reservation reservation, int number) {

		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		Reservation reservationToChange;
		
		try {
			MongoCollection<Reservation> col = database.getCollection(collectionName, Reservation.class)
					   .withCodecRegistry(pojoCodecRegistry);
			reservationToChange= col.find(eq("number", number)).first();
			
			col.updateOne(eq("number", number),combine
						   (set("firstName", reservation.getFirstName()), 
							set("lastName", reservation.getLastName()), 
							set("date", reservation.getDate()), 
							set("telephone", reservation.getTelephone())));
			reservation.setNumber(number);
		}
		finally{
			mongoDBClass.closeConnection();
			
		}
		
		if(reservationToChange == null) {
			throw new DataNotFoundException("Reservation with number: " + number + " not found and not updated");
		}
		
		return reservation;
	}
	
	public Reservation removeReservation(int number) {
		
		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		Reservation reservationToDelete;
		
		try {
			MongoCollection<Reservation> col = database.getCollection(collectionName, Reservation.class)
					   .withCodecRegistry(pojoCodecRegistry);
			reservationToDelete= col.find(eq("number", number)).first();
			col.deleteOne(eq("number", number));;
			
		}
		finally{
			mongoDBClass.closeConnection();
			
		}
		
		if(reservationToDelete == null) {
			throw new DataNotFoundException("Reservation with number: " + number + " not found and not deleted");
		}
		
		return reservationToDelete;
	}
}

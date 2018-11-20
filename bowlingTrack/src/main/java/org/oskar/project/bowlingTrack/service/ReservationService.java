package org.oskar.project.bowlingTrack.service;


import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.oskar.project.bowlingTrack.database.MongoDBClass;
import org.oskar.project.bowlingTrack.model.Message;
import org.oskar.project.bowlingTrack.model.Reservation;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;

public class ReservationService {
	
	private String collectionName = "reservations";
	
	MongoDBClass mongoDBClass = new MongoDBClass();
	
	
	public ReservationService() {
		
	}
	
	
	public List<Document> getAllReservations(){
		
		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		
		List<Document> list = new ArrayList<Document>();
		MongoCollection<Document> col = database.getCollection(collectionName);

		 FindIterable<Document> it = col.find();
	        
	        ArrayList<Document> docs = new ArrayList<>();
	        
	        it.into(docs);
	
	        for (Document doc : docs) {
	        	list.add(doc);
	        }  
		
				       /*
				        col.find().forEach(new Block<Document>() {
				            @Override
				            public void apply(final Document document) {
				                list.add(document);
				            }
				        });       
				        
						*/
        mongoDBClass.closeConnection();
		return list;
	}
	
	
	public Document getReservation(long number) {
		
		
		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		
		
		ArrayList<Document> docs = new ArrayList<>();
		
		try {
			MongoCollection<Document> col = database.getCollection(collectionName);
			FindIterable<Document> it = col.find(eq("number", number));
			
	        it.into(docs);
		
		}
		finally{
			
			mongoDBClass.closeConnection();
			
		}
		return docs.get(0);
	}
	
	public Reservation addReservation(Reservation reservation) {
		
		
		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		
		
		MongoCollection<Document> col = database.getCollection(collectionName);
		
		FindIterable<Document> it = col.find().sort(new Document("number", -1));
		ArrayList<Document> docs = new ArrayList<>();
        
        it.into(docs);
		
        ArrayList<Object> list = new ArrayList<>(docs.get(0).values());
		
		
		try {	    
			
			//get highest number of reservation in DB
			
	        String newId =  list.get(1).toString();
	        double x = Double.parseDouble(newId.trim());
			reservation.setNumber((int)x + 1);
			
			//Creating new Document from input
			 Document d = new Document("_id", new ObjectId());
		        d.append("number"    , reservation.getNumber());
		        d.append("first_name", reservation.getFirstName());
		        d.append("last_name" , reservation.getLastName());
		        d.append("date"		 , reservation.getDate());
		        d.append("telephone" , reservation.getTelephone());

		        col.insertOne(d);
			
		} 
		finally{
			
			mongoDBClass.closeConnection();
		}
		 
		
		return reservation;
	}
	
	public Document updateMessage(Message message) {
		//if(message.getId() <= 0) {
		//	return null;
		//} 
		return null;
	}
	
	public Document removeMessage(long id) {
		return null;
	}
}

package org.oskar.project.bowlingTrack.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Path("/test")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class testR {
	
	
	@GET
	public String getMessages() {


		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		
		MongoDatabase database = mongoClient.getDatabase("bowling_track");
		
	
		MongoCollection<Document> collection = database.getCollection("reservation");
		
		Document myDoc = collection.find().first();
		mongoClient.close();
			
		return  myDoc.toJson();
		
		
	}
}

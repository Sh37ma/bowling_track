package org.oskar.project.bowlingTrack.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


public class MongoDBClass {
	
	
	
	 private MongoClient mongoClient;
	 private MongoDatabase database;
	
	
	public MongoDBClass(){
	}
	
	
	public void openConnection() {
		mongoClient = new MongoClient();
		database = mongoClient.getDatabase("bowling_track");
	}

	public void closeConnection() {
		mongoClient.close();
	}
	
	public MongoClient getMongoClient() {
		return mongoClient;
	}


	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}


	public MongoDatabase getDatabase() {
		return database;
	}


	public void setDatabase(MongoDatabase database) {
		this.database = database;
	}


}

package org.oskar.project.bowlingTrack.database;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


public class MongoDBClass {
	
	
	
	 private MongoClient mongoClient;
	 private MongoDatabase database;
	 private CodecRegistry pojoCodecRegistry;


	public MongoDBClass(){
		 pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
				 fromProviders(PojoCodecProvider.builder().automatic(true).build()));
	}
	
	public CodecRegistry getPojoCodecRegistry() {
		return pojoCodecRegistry;
	}


	public void setPojoCodecRegistry(CodecRegistry pojoCodecRegistry) {
		this.pojoCodecRegistry = pojoCodecRegistry;
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

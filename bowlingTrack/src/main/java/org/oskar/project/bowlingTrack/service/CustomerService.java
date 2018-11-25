package org.oskar.project.bowlingTrack.service;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.oskar.project.bowlingTrack.database.MongoDBClass;
import org.oskar.project.bowlingTrack.exception.DataNotFoundException;
import org.oskar.project.bowlingTrack.model.Customer;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class CustomerService {

	private String collectionName = "customers";
	private MongoDBClass mongoDBClass = new MongoDBClass();
	private CodecRegistry pojoCodecRegistry = mongoDBClass.getPojoCodecRegistry();
	
	public CustomerService() {
	}
	
	
	
	public List<Customer> getAllCustomers(){
	      
		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		MongoCollection<Customer> col = database.getCollection(collectionName, Customer.class)
			   									   .withCodecRegistry(pojoCodecRegistry);
		List<Customer> list = new ArrayList<Customer>();
		
		try {
		    Block<Customer> addBlock = new Block<Customer>() {
		    	@Override
		    	public void apply(final Customer c) {
		    		list.add(c);
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
	
	
	public Customer getCustomer(String userName) {
		
		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		MongoCollection<Customer> col = database.getCollection(collectionName, Customer.class)
				   .withCodecRegistry(pojoCodecRegistry);
		Customer customer;
		
		try {
			customer = col.find(eq("userName", userName)).first();
			
		}
		finally{
			mongoDBClass.closeConnection();
			
		}
		
		if(customer == null) {
			throw new DataNotFoundException("Customer with number: " + userName + " not found");
		}
		
		return customer;
	}
	
	
	public Customer addCustomer(Customer newCustomer) {
		
		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
				
		MongoCollection<Customer> col = database.getCollection(collectionName, Customer.class)
				   .withCodecRegistry(pojoCodecRegistry);
	
		try {	    
			//TODO Check if userName is not taken
			//Inserting new Customer		  
		    col.insertOne(newCustomer);
		} 
		finally{
			
			mongoDBClass.closeConnection();
		}
		 
		return newCustomer;
	}
	
	public Customer updateCustomer(Customer customer, String userName) {

		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		MongoCollection<Customer> col = database.getCollection(collectionName, Customer.class)
				   .withCodecRegistry(pojoCodecRegistry);
		
		Customer customerToChange = col.find(eq("userName", userName)).first();
		
		if(customerToChange == null) {
			throw new DataNotFoundException("Customer with number: " + userName + " not found and not updated");
		}
		
		try {
			col.updateOne(eq("userName", userName),combine
						   (set("userName", customer.getUserName()), 
							set("password", customer.getPassword()), 
							set("reservations", customer.getReservations())));
		
		}
		finally{
			mongoDBClass.closeConnection();
			
		}
		return customer;
	}
	
	public Customer removeCustomer(String userName) {
		
		mongoDBClass.openConnection();
		MongoDatabase database = mongoDBClass.getDatabase();
		Customer customerToDelete;
		
		try {
			MongoCollection<Customer> col = database.getCollection(collectionName, Customer.class)
					   .withCodecRegistry(pojoCodecRegistry);
			customerToDelete= col.find(eq("userName", userName)).first();
			col.deleteOne(eq("userName", userName));;
			
		}
		finally{
			mongoDBClass.closeConnection();
			
		}
		
		if(customerToDelete == null) {
			throw new DataNotFoundException("Customer with userName: " + userName + " not found and not deleted");
		}
		
		return customerToDelete;
	}
	
}

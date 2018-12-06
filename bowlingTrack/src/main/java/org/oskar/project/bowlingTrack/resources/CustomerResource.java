package org.oskar.project.bowlingTrack.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.oskar.project.bowlingTrack.model.Customer;
import org.oskar.project.bowlingTrack.service.CustomerService;

@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

	
	CustomerService customerService = new CustomerService();
	
	@GET
	public List<Customer> getAllCustomers() {
		
		return customerService.getAllCustomers();
	}
	
	@GET
	@Path("/{customerUserName}")
	public Customer getCustomer(@PathParam("customerUserName") String userName) {
		
		return customerService.getCustomer(userName);
	}
	
	
	@POST
	public Response addCustomer(Customer customer) {
		
		Customer newCustomer = customerService.addCustomer(customer);
		
		return Response.status(Status.CREATED).entity(newCustomer).build();
	}
	
	
	@PUT
	@Path("/{customerUserName}")
	public Response updateCustomer(@PathParam("customerUserName") String userName, Customer customer) {
		
		Customer updatedCustomer = customerService.updateCustomer(customer, userName);
		
		return Response.status(Status.OK).entity(updatedCustomer).build();
	}
	
	@DELETE
	@Path("/{customerUserName}")
	public Response deleteCustomer(@PathParam("customerUserName") String userName) {
		
		Customer deletedCustomer = customerService.removeCustomer(userName);
		
		return Response.status(Status.OK).entity(deletedCustomer).build();
		
	}
	
}

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

import org.oskar.project.bowlingTrack.model.FreeDate;
import org.oskar.project.bowlingTrack.service.FreeDateService;

@Path("/freedates")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FreeDateResource {

	FreeDateService freeDateService = new FreeDateService();
	
	@GET
	public List<FreeDate> getAllFreeDates() {
		
		return freeDateService.getAllFreeDates();
	}
	
	@GET
	@Path("/{freeDateWeekNumber}")
	public FreeDate getFreeDate(@PathParam("freeDateWeekNumber") int weekNumber) {
		
		return freeDateService.getFreeDate(weekNumber);
	}
	
	
	@POST
	public Response addFreeDate(FreeDate freeDate) {
		
		FreeDate newFreeDate = freeDateService.addFreeDate(freeDate);
		
		return Response.status(Status.CREATED).entity(newFreeDate).build();
	}
	
	@PUT
	@Path("/{freeDateWeekNumber}")
	public Response updateFreeDate(@PathParam("freeDateWeekNumber") int weekNumber, FreeDate freeDate) {
		
		FreeDate updatedFreeDate = freeDateService.updateEntireFreeDate(freeDate, weekNumber);
		
		return Response.status(Status.OK).entity(updatedFreeDate).build();
	}
	
	@PUT
	@Path("/{freeDateWeekNumber}/{dayOfTheWeek}")
	public Response updateFreeDate(@PathParam("freeDateWeekNumber") int weekNumber,
									  FreeDate freeDate,
									  @PathParam("dayOfTheWeek") String day) {
		
		FreeDate updatedFreeDate = freeDateService.updateDay(weekNumber, freeDate, day);
		
		return Response.status(Status.OK).entity(updatedFreeDate).build();
	}
	
	@DELETE
	@Path("/{freeDateWeekNumber}")
	public Response deleteFreeDate(@PathParam("freeDateWeekNumber") int weekNumber) {
		
		FreeDate deletedFreeDate = freeDateService.removeFreeDate(weekNumber);
		
		return Response.status(Status.OK).entity(deletedFreeDate).build();
		
	}	
	
}

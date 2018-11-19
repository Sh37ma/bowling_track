package org.oskar.project.bowlingTrack.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;
import org.oskar.project.bowlingTrack.model.Reservation;
import org.oskar.project.bowlingTrack.service.ReservationService;





@Path("/reservations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReservationResource {

	ReservationService reservationService = new ReservationService();
	
	@GET
	public List<Document> getReservations() {
		
		return reservationService.getAllReservations();
	}
	
	@GET
	@Path("/{reservationNumber}")
	public Document getReservation(@PathParam("reservationNumber") long number) {
		
		return reservationService.getReservation(number);
	}
	
	@POST
	public Reservation addReservation() {
		
		return reservationService.addReservation();
	}
}

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

import org.oskar.project.bowlingTrack.model.Reservation;
import org.oskar.project.bowlingTrack.service.ReservationService;


@Path("/reservations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReservationResource {

	ReservationService reservationService = new ReservationService();
	
	@GET
	public List<Reservation> getReservations() {
		
		return reservationService.getAllReservations();
	}
	
	@GET
	@Path("/{reservationNumber}")
	public Reservation getReservation(@PathParam("reservationNumber") int number) {
		
		return reservationService.getReservation(number);
	}
	
	@POST
	public Response addReservation(Reservation reservation) {
		
		Reservation newReservation = reservationService.addReservation(reservation);
		
		return Response.status(Status.CREATED).entity(newReservation).build();
	}
	
	@PUT
	@Path("/{reservationNumber}")
	public Response updateReservation(@PathParam("reservationNumber") int number, Reservation reservation) {
		
		Reservation updatedReservation = reservationService.updateReservation(reservation, number);
		
		return Response.status(Status.OK).entity(updatedReservation).build();
	}
	
	@DELETE
	@Path("/{reservationNumber}")
	public Response deleteReservation(@PathParam("reservationNumber") int number) {
		
		Reservation deletedReservation = reservationService.removeReservation(number);
		
		return Response.status(Status.OK).entity(deletedReservation).build();
		
	}
	
	
	
}

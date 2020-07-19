package org.cinema.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "ticketProj" , types= {Ticket.class})
public interface TicketProj {
	public Long getId();
	public String GetNameClient();
	public double getPrix();
	public Integer getCodePaiement();
	public Place getPlace();
	public  boolean getReservee();
}

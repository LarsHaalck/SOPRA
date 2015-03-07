package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Ticket;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormShowTickets;

import java.util.ArrayList;

/**
 * Created by Patricia on 07.03.2015.
 */
public class ControllerFormShowTickets extends Controller
{
	private ControllerDatabase controllerDatabase;
	private ArrayList<Ticket> tickets;
	private FormShowTickets formShowTickets;

	public ControllerFormShowTickets(FormShowTickets form)
	{
		controllerDatabase = ControllerDatabase.getInstance();
		this.tickets = controllerDatabase.getTickets();
		this.formShowTickets = form;
	}

	@Override
	protected void addListeners()
	{
		//do I really need listener?
	}

	@Override
	protected void removeListeners()
	{

	}

	public void fill(){

		//for loop for all tickets
		for(Ticket t: tickets)
		{
			formShowTickets.addPanel(t.getName(), t.getPrice(), t.getNumPassengers(), t.getDescription());
		}

	}
}

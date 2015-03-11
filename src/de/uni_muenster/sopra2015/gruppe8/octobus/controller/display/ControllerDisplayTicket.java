package de.uni_muenster.sopra2015.gruppe8.octobus.controller.display;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterDisplay;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Ticket;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.displays.DisplayTicket;
import java.util.ArrayList;

/**
 * Controller for DisplayTicket
 */
public class ControllerDisplayTicket extends Controller implements ListenerButton
{
	private ControllerDatabase controllerDatabase;
	private ArrayList<Ticket> tickets;
	private DisplayTicket displayTicket;

	public ControllerDisplayTicket(DisplayTicket displayTicket)
	{
		super();

		controllerDatabase = ControllerDatabase.getInstance();
		this.tickets = controllerDatabase.getTickets();
		this.displayTicket = displayTicket;
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener(this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener(this);
	}

	/**
	 * Adds a Panel to DisplayTicket for every Ticket in the database.
	 */
	public void fill(){
		int i =0;

		//for loop for all tickets
		for(Ticket t: tickets)
		{
			i = i +30;
			displayTicket.addPanel(t.getName(), t.getPrice(), t.getNumPassengers(), t.getDescription(), i);
		}
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch (btn)
		{
			case DISPLAY_TICKET_BACK:
				ControllerManager.informDisplaySwitch(EmitterDisplay.DISPLAY_MAIN);
		}
	}
}

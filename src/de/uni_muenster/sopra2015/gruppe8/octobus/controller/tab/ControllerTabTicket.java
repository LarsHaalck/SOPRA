package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Ticket;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabTicket;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TablePrice;

import java.util.ArrayList;

/**
 * Controller for TabTicket class.
 * @pre User is logged in and has Ticket-Planner-Role
 */
public class ControllerTabTicket extends Controller implements ListenerButton, ListenerTable
{
	private TabTicket tabTicket;
	private ControllerDatabase controllerDatabase;


	public ControllerTabTicket(TabTicket tabTicket)
	{
		super();
		controllerDatabase = ControllerDatabase.getInstance();
		this.tabTicket = tabTicket;
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerTable)this);
		ControllerManager.addListener((ListenerButton)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerTable)this);
		ControllerManager.removeListener((ListenerButton)this);
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch(btn)
		{
			case TAB_TICKET_NEW:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_TICKET_NEW);
				break;

			case TAB_TICKET_EDIT:
				if(tabTicket.getSelectedID() != -1)
				{
					ControllerManager.informWindowOpen(EmitterWindow.FORM_TICKET_EDIT, tabTicket.getSelectedID());
				}
				else
				{
					tabTicket.showMessageDialog("Um eine Fahrkarte zu bearbeiten wählen Sie bitte einen Eintrag aus der Tabelle.");
				}
				break;

			case TAB_TICKET_DELETE:
				if(tabTicket.getSelectedID() != -1)
				{
					if(tabTicket.showConfirmDialog("Wirklich löschen?"))
					{
						controllerDatabase.deleteTicket(tabTicket.getSelectedID());
						fillTable();
					}
				}
				else
				{
					tabTicket.showMessageDialog("Um eine Fahrkarte zu löschen wählen Sie bitte einen Eintrag aus der Tabelle.");
				}
				break;
		}
	}

	/**
	 * Used to fill table with tickets from DB.
	 */
	public void fillTable()
	{
		ArrayList<Ticket> tickets = controllerDatabase.getTickets();
		Object[][] data = new Object[tickets.size()][4];
		for(int i=0; i<tickets.size(); i++)
		{
			Ticket ticket = tickets.get(i);
			data[i][0] = ticket.getId();
			data[i][1] = ticket.getName();
			data[i][2] = new TablePrice(ticket.getPrice());
			data[i][3] = ticket.getNumPassengers();
		}
		tabTicket.fillTable(data);
	}

	@Override
	public void tableSelectionChanged(EmitterTable emitter)
	{

	}

	@Override
	public void tableContentChanged(EmitterTable emitter)
	{
		switch(emitter)
		{
			case TAB_TICKET:
				fillTable();
				break;
		}
	}

	@Override
	public void tableFocusLost(EmitterTable emitter)
	{

	}
}

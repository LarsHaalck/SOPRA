package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Bus;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabBus;

import java.util.ArrayList;

/**
 * Controller for TabBus-class
 */
public class ControllerTabBus extends Controller implements ListenerButton
{
	private TabBus tabBus;
	private ControllerDatabase controllerDatabase;

	public ControllerTabBus(TabBus tabBus)
	{
		super();
		controllerDatabase = ControllerDatabase.getInstance();
		this.tabBus = tabBus;
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch(btn)
		{
			case TAB_BUS_DELETE:
				break;

			case TAB_BUS_EDIT:
				//TODO
				ControllerManager.informWindowOpen(EmitterWindow.FORM_BUS_EDIT, tabBus.getSelectedID());
				break;

			case TAB_BUS_NEW:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_BUS_NEW);
				break;
		}
	}

	public void fillTable()
	{
		ArrayList<Bus> buses = controllerDatabase.getBuses();
		Object[][] data = new Object[buses.size()][8];
		for(int i=0; i<buses.size(); i++)
		{
			Bus bus = buses.get(i);
			data[i][0] = bus.getId();
			data[i][1] = bus.getLicencePlate();
			data[i][2] = bus.getManufacturer();
			data[i][3] = bus.getModel();
			data[i][4] = bus.isArticulatedBus() ? "Gelenkbus" : "Normal";
			data[i][5] = bus.getNumberOfSeats();
			data[i][6] = bus.getStandingRoom();
			data[i][7] = bus.getNextInspectionDue().toString();
		}
		tabBus.fillTable(data);
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
	}
}

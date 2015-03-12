package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Bus;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabBus;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableDate;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Controller for TabBus-class
 */
public class ControllerTabBus extends Controller implements ListenerButton, ListenerTable
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
				if(tabBus.getSelectedID() != -1)
				{
					int num = controllerDatabase.getNumberOfToursUsingBusId(tabBus.getSelectedID());
					String add = "";
					if(num > 0)
						add = " Der Bus wird noch für "+num+" Fahrten genutzt.";
					if(tabBus.showConfirmDialog("Wirklich löschen?"+add))
					{
						controllerDatabase.deleteBus(tabBus.getSelectedID());
						fillTable();
						ControllerManager.informTableContentChanged(EmitterTable.TAB_SCHEDULE);
						ControllerManager.informTableContentChanged(EmitterTable.TAB_WORKPLAN);
					}
				}
				else
				{
					tabBus.showMessageDialog("Um einen Bus zu löschen wählen Sie bitte einen Eintrag aus der Tabelle.");
				}
				break;

			case TAB_BUS_EDIT:
				if(tabBus.getSelectedID() != -1)
				{
					ControllerManager.informWindowOpen(EmitterWindow.FORM_BUS_EDIT, tabBus.getSelectedID());
				}
				else
				{
					tabBus.showMessageDialog("Um einen Bus zu bearbeiten wählen Sie bitte einen Eintrag aus der Tabelle.");
				}
				break;

			case TAB_BUS_NEW:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_BUS_NEW);
				break;
		}
	}

	/**
	 * Used to fill the table with buses from DB.
	 */
	public void fillTable()
	{
		ArrayList<Bus> buses = controllerDatabase.getBuses();
		Object[][] data = new Object[buses.size()][9];

		//Images for small state-circles
		ImageIcon imageRed = new ImageIcon("res/images/red.png");
		ImageIcon imageYellow = new ImageIcon("res/images/grelb.png");
		ImageIcon imageGreen = new ImageIcon("res/images/green.png");
		//Check for next inspection
		Date today = new Date();
		//Add milliseconds of 4 week -> 28 days
		long time = today.getTime() + (1440l * 28l * 60l * 1000l);
		Date fourWeeks = new Date(time);
		for(int i=0; i<buses.size(); i++)
		{
			Bus bus = buses.get(i);
			data[i][0] = bus.getId();
			data[i][1] = bus.getLicencePlate();
			data[i][2] = bus.getManufacturer();
			data[i][3] = bus.getModel();
			data[i][4] = bus.isArticulatedBus();
			data[i][5] = bus.getNumberOfSeats();
			data[i][6] = bus.getStandingRoom();
			data[i][7] = new TableDate(bus.getNextInspectionDue(), TableDate.Type.DATE);
			if(bus.getNextInspectionDue().before(today))
				data[i][8] = imageRed;
			else if(bus.getNextInspectionDue().before(fourWeeks))
				data[i][8] = imageYellow;
			else
				data[i][8] = imageGreen;
		}
		tabBus.fillTable(data);
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
		ControllerManager.addListener((ListenerTable)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton)this);
		ControllerManager.removeListener((ListenerTable)this);
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
			case TAB_BUS:
				fillTable();
				break;
		}
	}

	@Override
	public void tableFocusLost(EmitterTable emitter)
	{

	}
}

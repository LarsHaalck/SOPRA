package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.BusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabBusStop;

import java.util.ArrayList;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerTabBusStop extends Controller implements ListenerButton, ListenerTable
{
	private TabBusStop tabBusStop;
	private ControllerDatabase controllerDatabase;

	public ControllerTabBusStop(TabBusStop tabBusStop)
	{
		this.tabBusStop = tabBusStop;
		this.controllerDatabase = ControllerDatabase.getInstance();
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerTable) this);
		ControllerManager.addListener((ListenerButton)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerTable) this);
		ControllerManager.addListener((ListenerButton)this);
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch(btn)
		{
			case TAB_BUS_STOP_NEW:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_BUS_STOP_NEW);
				break;

			case TAB_BUS_STOP_EDIT:
				if(tabBusStop.getSelectedID() != -1)
				{
					ControllerManager.informWindowOpen(EmitterWindow.FORM_BUS_STOP_EDIT, tabBusStop.getSelectedID());
				}
				else
				{
					tabBusStop.showMessageDialog("Um eine Haltestelle zu bearbeiten wählen Sie bitte einen Eintrag aus der Tabelle.");
				}
				break;

			case TAB_BUS_STOP_DELETE:
				if(tabBusStop.getSelectedID() != -1)
				{
					if(tabBusStop.showConfirmDialog("Wirklich löschen?"))
					{
						controllerDatabase.deleteBusStop(tabBusStop.getSelectedID());
						fillTable();
					}
				}
				else
				{
					tabBusStop.showMessageDialog("Um eine Haltestelle zu löschen wählen Sie bitte einen Eintrag aus der Tabelle.");
				}
				break;
		}
	}

	public void fillTable()
	{
		ArrayList<BusStop> busStops = controllerDatabase.getBusStops();
		Object[][] data = new Object[busStops.size()][4];
		for(int i=0; i<busStops.size(); i++)
		{
			BusStop busStop = busStops.get(i);
			data[i][0] = busStop.getId();
			data[i][1] = busStop.getName();
			data[i][2] = busStop.isBarrierFree();
			data[i][3] = busStop.getStoppingPointsNum();
		}
		tabBusStop.fillTable(data);
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
			case TAB_BUSSTOP:
				fillTable();
				break;
		}
	}
}

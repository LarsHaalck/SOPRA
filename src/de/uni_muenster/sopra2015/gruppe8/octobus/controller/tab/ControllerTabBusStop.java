package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.BusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabBusStop;

import java.util.ArrayList;

/**
 * Controller for TabBusStop class.
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
						ArrayList<String> routes = controllerDatabase.getRouteNamesUsingBusStopId(tabBusStop.getSelectedID());
						if (routes.size() == 0)
						{
							controllerDatabase.deleteBusStop(tabBusStop.getSelectedID());
							fillTable();
						}
						else
						{
							String routesNames = "";
							routesNames += routes.get(0);
							for (int i=1; i<routes.size(); i++)
							{
								routesNames += "\n"+routes.get(i);
							}

							tabBusStop.showMessageDialog("Haltestelle kann nicht gelöscht werden, da sie in folgenden Routen noch verwendet wird: \n"+routesNames);
						}

					}
				}
				else
				{
					tabBusStop.showMessageDialog("Um eine Haltestelle zu löschen wählen Sie bitte einen Eintrag aus der Tabelle.");
				}
				break;

			case TAB_BUS_STOP_PRINT:
				if(tabBusStop.getSelectedID() != -1)
				{
					ControllerManager.informWindowOpen(EmitterWindow.FORM_BUS_STOP_PRINT, tabBusStop.getSelectedID());
				}
				else
				{
					tabBusStop.showMessageDialog("Um eine Haltestelle zu drucken wählen Sie bitte einen Eintrag aus der Tabelle.");
				}
		}
	}

	/**
	 * Used to fill table with bus stops from DB.
	 */
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

	@Override
	public void tableFocusLost(EmitterTable emitter)
	{

	}
}

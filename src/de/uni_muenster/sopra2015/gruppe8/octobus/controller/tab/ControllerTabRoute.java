package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Route;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabRoute;

import java.util.ArrayList;

/**
 * Controller for TabRoute class.
 * @pre User is logged in and has Network-Planner-Role.
 */
public class ControllerTabRoute extends Controller implements ListenerButton, ListenerTable
{
	private TabRoute tabRoute;
	private ControllerDatabase controllerDatabase;

	public ControllerTabRoute(TabRoute tabRoute)
	{
		this.tabRoute = tabRoute;
		this.controllerDatabase = ControllerDatabase.getInstance();
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
		ControllerManager.addListener((ListenerTable) this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton) this);
		ControllerManager.removeListener((ListenerTable) this);
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch (btn)
		{
			case TAB_ROUTE_NEW:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_ROUTE_NEW);
				break;

			case TAB_ROUTE_EDIT:
				if(tabRoute.getSelectedID() != -1)
				{
					ControllerManager.informWindowOpen(EmitterWindow.FORM_ROUTE_EDIT, tabRoute.getSelectedID());
				}
				else
				{
					tabRoute.showMessageDialog("Um eine Linie zu bearbeiten wählen Sie bitte einen Eintrag aus der Tabelle.");
				}
				break;

			case TAB_ROUTE_DELETE:

				if(tabRoute.getSelectedID() != -1)
				{
					if(tabRoute.showConfirmDialog("Wirklich löschen?"))
					{
						controllerDatabase.deleteRoute(tabRoute.getSelectedID());
						fillTable();
						ControllerManager.informTableContentChanged(EmitterTable.TAB_SCHEDULE);
						ControllerManager.informTableContentChanged(EmitterTable.TAB_WORKPLAN);
						ControllerManager.informTableContentChanged(EmitterTable.TAB_NETWORK);
					}
				}
				else
				{
					tabRoute.showMessageDialog("Um eine Linie zu löschen wählen Sie bitte einen Eintrag aus der Tabelle.");
				}
				break;
		}
	}

	/**
	 * Used to fill table with routes from DB.
	 */
	public void fillTable()
	{
		ArrayList<Route> routes = controllerDatabase.getRoutes();
		Object[][] data = new Object[routes.size()][5];
		for(int i=0; i<routes.size(); i++)
		{
			Route route = routes.get(i);
			data[i][0] = route.getId();
			data[i][1] = route.getName();
			data[i][2] = route.getStart().getName();
			data[i][3] = route.getEnd().getName();
			data[i][4] = route.isNight();
		}
		tabRoute.fillTable(data);
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
			case TAB_ROUTE:
				fillTable();
				break;
		}
	}

	@Override
	public void tableFocusLost(EmitterTable emitter)
	{

	}
}

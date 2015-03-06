package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Route;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabRoute;

import java.util.ArrayList;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerTabRoute extends Controller implements ListenerButton
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
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton)this);
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch (btn)
		{
			case TAB_LINE_NEW:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_ROUTE_NEW);
				break;

			case TAB_LINE_EDIT:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_ROUTE_EDIT, tabRoute.getSelectedID());
				break;

			case TAB_LINE_DELETE:
				break;
		}
	}

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
}

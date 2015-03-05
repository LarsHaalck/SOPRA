package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabBusStop;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerTabBusStop extends Controller implements ListenerButton
{
	private TabBusStop tabBusStop;

	public ControllerTabBusStop(TabBusStop tabBusStop)
	{
		this.tabBusStop = tabBusStop;
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

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch(btn)
		{
			case TAB_BUS_STOP_NEW:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_BUS_STOP_NEW);
				break;

			case TAB_BUS_STOP_EDIT:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_BUS_STOP_EDIT, tabBusStop.getSelectedID());
				break;

			case TAB_BUS_STOP_DELETE:
				break;
		}
	}
}
